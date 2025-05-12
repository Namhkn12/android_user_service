package com.namhkn.userservice.service;

import com.namhkn.userservice.UserserviceApplication;
import com.namhkn.userservice.dto.LoginRequest;
import com.namhkn.userservice.dto.RegisterRequest;
import com.namhkn.userservice.dto.UserDTO;
import com.namhkn.userservice.model.UserCredential;
import com.namhkn.userservice.model.UserInfo;
import com.namhkn.userservice.repository.UserCredentialRepository;
import com.namhkn.userservice.repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {
    private final UserCredentialRepository repository;
    private final UserRepository userRepository;

    public @Nullable UserDTO login(LoginRequest request) {
        UserCredential credential = repository.findByUsername(request.getUsername()).orElseThrow();
        if (request.getPassword().equals(credential.getPassword())) {
            UserInfo info = credential.getUserInfo();
            return new UserDTO(credential.getId(), info.getDisplayName(), info.getAddressList(), info.getPhoneNumber(), info.getGender(), info.getDateOfBirth());
        }
        return null;
    }

    public boolean registerUser(RegisterRequest request) {
        Optional<UserCredential> optional = repository.findByUsername(request.getUsername());
        if (optional.isPresent()) { //Username already exist
            return false;
        }
        UserCredential userCredential = new UserCredential();
        UserInfo userInfo = new UserInfo();
        userInfo.setDisplayName("New User");
        userRepository.save(userInfo);
        userCredential.setUsername(request.getUsername());
        userCredential.setPassword(request.getPassword());
        userCredential.setUserInfo(userInfo);
        repository.save(userCredential);
        return true;
    }
}
