package com.namhkn.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateDOBRequest {
    private LocalDate dateOfBirth;
}
