package com.namhkn.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressDTO {
    private String city;
    private String road;
    private String phoneNumber;
}