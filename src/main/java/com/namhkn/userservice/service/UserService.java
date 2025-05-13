package com.namhkn.userservice.service;

import com.namhkn.userservice.dto.UpdatePasswordRequest;
import com.namhkn.userservice.dto.UserAddressDTO;
import com.namhkn.userservice.model.UserAddress;
import com.namhkn.userservice.model.UserCredential;
import com.namhkn.userservice.model.UserInfo;
import com.namhkn.userservice.repository.UserAddressRepository;
import com.namhkn.userservice.repository.UserCredentialRepository;
import com.namhkn.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;
    private final UserCredentialRepository credentialRepository;

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

    public void updatePassword(int userId, UpdatePasswordRequest request) {
        List<UserCredential> credentials = credentialRepository.findAll();
        for (UserCredential credential : credentials) {
            if (credential.getUserInfo().getId() == userId) {
                if (credential.getPassword().equals(request.getCurrentPassword())) {
                    credential.setPassword(request.getNewPassword());
                    credentialRepository.save(credential);
                }
            }
        }
    }

}
