package com.namhkn.userservice.service;

import com.namhkn.userservice.model.User;
import com.namhkn.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String id) {
        return userRepository.findById(Integer.valueOf(id)).orElseThrow();
    }
}
