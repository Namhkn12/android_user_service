package com.namhkn.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateEmailRequest {
    private String username;
    private String newEmail;
    private String code;
}
