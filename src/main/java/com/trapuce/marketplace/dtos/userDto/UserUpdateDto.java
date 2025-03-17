package com.trapuce.marketplace.dtos.userDto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateDto {
    @Email(message = "Format d'email invalide")
    private String email;
    
    private String phoneNumber;
    private String address;
    private String city;
    private String postalCode;
    private String profilePictureUrl;

}
