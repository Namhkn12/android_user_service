package com.namhkn.userservice.controller;

import com.namhkn.userservice.dto.LoginRequest;
import com.namhkn.userservice.dto.UserDTO;
import com.namhkn.userservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService service;

    @PostMapping
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request) {
        UserDTO result = service.login(request);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(null);
    }
}
