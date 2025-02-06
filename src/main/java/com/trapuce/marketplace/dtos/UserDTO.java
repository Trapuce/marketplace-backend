package com.trapuce.marketplace.dtos;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;


/**
 * DTO representing user data sent to the frontend.
 * Contains only necessary fields to avoid exposing sensitive information.
 */
@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private ImageDTO profilePicture;
    private Date registrationDate;
   
}
