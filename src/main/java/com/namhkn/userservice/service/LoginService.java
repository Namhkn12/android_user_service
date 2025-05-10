package com.namhkn.userservice.service;

import com.namhkn.userservice.dto.LoginRequest;
import com.namhkn.userservice.dto.UserDTO;
import com.namhkn.userservice.model.UserCredential;
import com.namhkn.userservice.model.UserInfo;
import com.namhkn.userservice.repository.UserCredentialRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private final UserCredentialRepository repository;

    public @Nullable UserDTO login(LoginRequest request) {
        UserCredential credential = repository.findByUsername(request.getUsername()).orElseThrow();
        if (request.getPassword().equals(credential.getPassword())) {
            UserInfo info = credential.getUserInfo();
            return new UserDTO(credential.getId(), info.getDisplayName(), info.getAddressList(), info.getPhoneNumber(), info.getGender(), info.getDateOfBirth());
        }
        return null;
    }
}
