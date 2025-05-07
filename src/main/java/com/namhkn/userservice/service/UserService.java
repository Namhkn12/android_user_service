package com.namhkn.userservice.service;

import com.namhkn.userservice.dto.UserAddressDTO;
import com.namhkn.userservice.model.UserAddress;
import com.namhkn.userservice.model.UserInfo;
import com.namhkn.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserInfo getUser(String id) {
        return userRepository.findById(Integer.valueOf(id)).orElseThrow();
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
            address.setCity(dto.getCity());
            address.setRoad(dto.getRoad());
            address.setPhoneNumber(dto.getPhoneNumber());
            address.setUserInfo(user);
            user.getAddressList().add(address);
        }
        userRepository.save(user);
    }

}
