package com.trapuce.marketplace.dtos.userDto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UserResponseDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;
    private String address;
    private String city;
    private String postalCode;
    private LocalDateTime registrationDate;
    private LocalDateTime lastConnectionDate;
    private Boolean isProfessional;
    private Boolean isVerified;


}