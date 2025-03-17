package com.trapuce.marketplace.dtos.userDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PasswordUpdateDto {
    @NotBlank(message = "L'ancien mot de passe est obligatoire")
    private String oldPassword;
    
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String newPassword;
}
