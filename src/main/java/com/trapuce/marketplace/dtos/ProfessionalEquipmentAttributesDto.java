package com.trapuce.marketplace.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfessionalEquipmentAttributesDto {

    @NotEmpty(message = "Le type d'équipement ne peut pas être vide.")
    @Size(max = 100, message = "Le type d'équipement ne peut pas dépasser 100 caractères.")
    private String equipmentType; 
    @NotEmpty(message = "La marque ne peut pas être vide.")
    @Size(max = 100, message = "La marque ne peut pas dépasser 100 caractères.")
    private String brand; 

    @NotEmpty(message = "Le modèle ne peut pas être vide.")
    @Size(max = 100, message = "Le modèle ne peut pas dépasser 100 caractères.")
    private String model; 

    @NotEmpty(message = "La condition de l'équipement est obligatoire.")
    @Pattern(regexp = "^(NEW|USED)$", message = "La condition de l'équipement doit être soit 'NEW' (neuf) ou 'USED' (usagé).")
    private String condition; 

    @Positive(message = "L'année doit être un nombre positif.")
    private Integer year; 

    @Size(max = 100, message = "La certification ne peut pas dépasser 100 caractères.")
    private String certification; 

    private Boolean warranty; 

    @Size(max = 255, message = "L'usage de l'équipement ne peut pas dépasser 255 caractères.")
    private String usage; 
}