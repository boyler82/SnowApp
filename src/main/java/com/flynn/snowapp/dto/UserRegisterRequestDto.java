package com.flynn.snowapp.dto;

import lombok.*;

@Data
public class UserRegisterRequestDto {
    private String email;
    private String password;
    private String username;
}