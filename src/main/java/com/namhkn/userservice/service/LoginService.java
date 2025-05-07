package com.namhkn.userservice.service;

import com.namhkn.userservice.dto.LoginRequest;
import com.namhkn.userservice.repository.UserCredentialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private final UserCredentialRepository repository;

    public boolean login(LoginRequest request) {
        return repository.findByUsername(request.getUsername())
                .map(cred -> cred.getPassword().equals(request.getPassword()))
                .orElse(false);
    }
}
