package com.trapuce.marketplace.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RealEstateAttributesDto {
    
    @NotNull(message = "La surface habitable ne peut pas être null.")
    @PositiveOrZero(message = "La surface habitable doit être un nombre positif ou égal à zéro.")
    private Float habitableSurface;  
    
    @NotNull(message = "Le nombre de pièces ne peut pas être null.")
    @Min(value = 1, message = "Le nombre de pièces doit être supérieur ou égal à 1.")
    private Integer numberOfRooms;  

    @NotNull(message = "Le nombre de chambres ne peut pas être null.")
    @Min(value = 1, message = "Le nombre de chambres doit être supérieur ou égal à 1.")
    private Integer numberOfBedrooms;  

    @Min(value = 0, message = "Le nombre de salles de bain ne peut pas être inférieur à zéro.")
    private Integer numberOfBathrooms;  

    @NotEmpty(message = "Le type de propriété ne peut pas être vide.")
    private String propertyType;  

    private Integer floor;  

    private Boolean hasElevator;  

    @Size(max = 50, message = "La performance énergétique ne peut pas dépasser 50 caractères.")
    private String energyPerformance;  
    @Size(max = 50, message = "Le type de chauffage ne peut pas dépasser 50 caractères.")
    private String heatingType;  

    private Boolean furnished;  

    @PositiveOrZero(message = "Les charges doivent être égales ou supérieures à zéro.")
    private Float charges;  

    @NotNull(message = "Le type de transaction (location ou vente) est obligatoire.")
    private String transactionType;  
    

}

