package com.namhkn.userservice.service;

import com.namhkn.userservice.data.VerificationCode;
import com.namhkn.userservice.dto.*;
import com.namhkn.userservice.model.UserAddress;
import com.namhkn.userservice.model.UserCredential;
import com.namhkn.userservice.model.UserInfo;
import com.namhkn.userservice.repository.UserAddressRepository;
import com.namhkn.userservice.repository.UserCredentialRepository;
import com.namhkn.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;
    private final UserCredentialRepository credentialRepository;

    private final JavaMailSender javaMailSender;
    private final Map<String, VerificationCode> passwordVerificationCode = new HashMap<>();
    private final Map<String, VerificationCode> emailVerificationCode = new HashMap<>();
    private final long EXPIRATION_TIME_MS = 5 * 60 * 1000; //5 sec

    public UserInfo getUser(String id) {
        return userRepository.findById(Integer.valueOf(id)).orElseThrow();
    }

    public List<UserAddress> getAddressesOfUser(int userId) {
        return userRepository.findById(userId).orElseThrow().getAddressList();
    }

    public void updateName(int userId, String newName) {
        UserInfo user = userRepository.findById(userId).orElseThrow();
        user.setDisplayName(newName);
        userRepository.save(user);
    }

    public void updateGender(int userId, String gender) {
        UserInfo user = userRepository.findById(userId).orElseThrow();
        user.setGender(gender);
        userRepository.save(user);
    }

    public void updateDOB(int userId, LocalDate dob) {
        UserInfo user = userRepository.findById(userId).orElseThrow();
        user.setDateOfBirth(dob);
        userRepository.save(user);
    }

    public void updatePhoneNumber(int userId, String phoneNumber) {
        UserInfo user = userRepository.findById(userId).orElseThrow();
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }

    public void updateAddresses(int userId, List<UserAddressDTO> addressDTOs) {
        UserInfo user = userRepository.findById(userId).orElseThrow();

        user.getAddressList().clear();
        for (UserAddressDTO dto : addressDTOs) {
            UserAddress address = new UserAddress();
            address.setName(dto.getName());
            address.setCity(dto.getCity());
            address.setRoad(dto.getRoad());
            address.setPhoneNumber(dto.getPhoneNumber());
            address.setUserInfo(user);
            user.getAddressList().add(address);
        }
        userRepository.save(user);
    }

    public void updateAddressById(int addressId, UserAddressDTO addressDTO) {
        UserAddress userAddress = addressRepository.findById(addressId).orElseThrow();
        userAddress.setName(addressDTO.getName());
        userAddress.setCity(addressDTO.getCity());
        userAddress.setRoad(addressDTO.getRoad());
        userAddress.setPhoneNumber(addressDTO.getPhoneNumber());

        addressRepository.save(userAddress);
    }

    public void addAddress(int userId, UserAddressDTO addressDTO) {
        UserInfo userInfo = userRepository.findById(userId).orElseThrow();
        UserAddress userAddress = new UserAddress();
        userAddress.setName(addressDTO.getName());
        userAddress.setRoad(addressDTO.getRoad());
        userAddress.setCity(addressDTO.getCity());
        userAddress.setPhoneNumber(addressDTO.getPhoneNumber());
        userAddress.setUserInfo(userInfo);
        addressRepository.save(userAddress);
    }

    public void removeAddress(int addressId) {
        UserAddress userAddress = addressRepository.findById(addressId).orElseThrow();
        addressRepository.delete(userAddress);
    }

    public boolean updateEmail(UpdateEmailRequest request) {
        boolean res = verifyEmailCode(request.getUsername(), request.getCode());
        if (res) {
            UserCredential userCredential = credentialRepository.findByUsername(request.getUsername()).orElseThrow();
            UserInfo userInfo = userCredential.getUserInfo();
            userInfo.setEmail(request.getNewEmail());
            userRepository.save(userInfo);
            emailVerificationCode.remove(request.getUsername());
            return true;
        }
        return false;
    }

    public boolean updatePassword(UpdatePasswordRequest request) {
        boolean res = verifyPasswordCode(request.getUsername(), request.getCode());
        if (res) {
            UserCredential userCredential = credentialRepository.findByUsername(request.getUsername()).orElseThrow();
            userCredential.setPassword(request.getNewPassword());
            credentialRepository.save(userCredential);
            passwordVerificationCode.remove(request.getUsername());
            return true;
        }
        return false;
    }

    private final Random random = new Random();

    public boolean requestConfirmEmail(ConfirmEmailRequest request) {
        Optional<UserCredential> optional = credentialRepository.findByUsername(request.getUsername());
        if (optional.isEmpty()) return false;
        UserCredential userCredential = optional.orElseThrow();
        UserInfo userInfo = userCredential.getUserInfo();
        String username = userCredential.getUsername();

        String code = String.valueOf(random.nextInt(100000, 999999));
        emailVerificationCode.put(userCredential.getUsername(), new VerificationCode(code, System.currentTimeMillis()));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userInfo.getEmail());
        message.setSubject("Furniture eCommerce - Xác thực email cho tài khoản: " + username);
        message.setText("Mã của bạn là: " + code + "\nMã sẽ hết hạn sau 5 phút." + "\n\n* Nếu bạn không yêu cầu xác thực email, hãy bỏ qua mail này.");
        javaMailSender.send(message);
        return true;
    }

    public boolean requestChangePassword(ChangePasswordRequest request) {
        Optional<UserCredential> optional = credentialRepository.findByUsername(request.getUsername());
        if (optional.isEmpty()) return false;
        UserCredential userCredential = optional.orElseThrow();
        UserInfo userInfo = userCredential.getUserInfo();
        String username = userCredential.getUsername();

        String code = String.valueOf(random.nextInt(100000, 999999));// 6-digit code
        passwordVerificationCode.put(userCredential.getUsername(), new VerificationCode(code, System.currentTimeMillis()));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userInfo.getEmail());
        message.setSubject("Furniture eCommerce - Đặt lại mật khẩu cho tài khoản: " + username);
        message.setText("Mã của bạn là: " + code + "\nMã sẽ hết hạn sau 5 phút." + "\n\n* Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua mail này.");
        javaMailSender.send(message);
        return true;
    }

    private boolean verifyPasswordCode(String username, String code) {
        VerificationCode verificationCode = passwordVerificationCode.get(username);
        if (verificationCode == null) return false; //No code
        long currentTime = System.currentTimeMillis();
        if (currentTime - verificationCode.getTimestamp() > EXPIRATION_TIME_MS) {
            passwordVerificationCode.remove(username);
            return false;
        }
        return code.equals(verificationCode.getCode());
    }

    private boolean verifyEmailCode(String username, String code) {
        VerificationCode verificationCode = emailVerificationCode.get(username);
        if (verificationCode == null) return false; //No code
        long currentTime = System.currentTimeMillis();
        if (currentTime - verificationCode.getTimestamp() > EXPIRATION_TIME_MS) {
            emailVerificationCode.remove(username);
            return false;
        }
        return code.equals(verificationCode.getCode());
    }

}
