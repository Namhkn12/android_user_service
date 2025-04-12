package com.namhkn.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String displayName;
    private String address;
    private String phoneNumber;
}
