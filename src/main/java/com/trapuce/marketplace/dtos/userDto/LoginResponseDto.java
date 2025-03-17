package com.trapuce.marketplace.dtos.userDto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private UserResponseDto user;
    
}
