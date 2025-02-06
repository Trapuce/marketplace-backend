package com.trapuce.marketplace.dtos;

import lombok.Data;

/**
 * DTO for authentication response.
 * Contains user data and JWT token after successful login.
 */
@Data
public class AuthResponseDTO {

    private UserDTO user;
    private String token;
}
