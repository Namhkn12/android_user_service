package com.namhkn.userservice.dto;

import com.namhkn.userservice.model.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String displayName;
    private List<UserAddress> addressList;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
}
