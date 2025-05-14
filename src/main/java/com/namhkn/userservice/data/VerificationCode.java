package com.namhkn.userservice.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificationCode {
    private String code;
    private long timestamp;
}
