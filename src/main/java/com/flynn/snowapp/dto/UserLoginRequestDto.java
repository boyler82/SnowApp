package com.flynn.snowapp.dto;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String email;
    private String password;
}
