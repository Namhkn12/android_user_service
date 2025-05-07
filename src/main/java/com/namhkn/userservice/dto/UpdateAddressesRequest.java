package com.namhkn.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateAddressesRequest {
    private List<UserAddressDTO> addresses;
}
