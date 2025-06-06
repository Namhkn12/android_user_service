package com.namhkn.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePasswordRequest {
    private String username;
    private String code;
    private String newPassword;
}
