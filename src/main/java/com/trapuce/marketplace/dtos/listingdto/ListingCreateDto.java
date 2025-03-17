package com.trapuce.marketplace.dtos.listingdto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ListingCreateDto {
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 255, message = "Le titre doit contenir entre 3 et 255 caractères")
    private String title;
    
    @NotBlank(message = "La description est obligatoire")
    @Size(min = 20, message = "La description doit contenir au moins 20 caractères")
    private String description;
    
    private BigDecimal price;
    private Boolean isPriceNegotiable = false;
    private String conditionState;
    
    @NotNull(message = "L'utilisateur est obligatoire")
    private UUID userId;

    @NotNull(message = "La catégorie est obligatoire")
    private UUID categoryId;
    
    private BigDecimal latitude;
    private BigDecimal longitude;
    
    private List<String> images = new ArrayList<>();

    @Valid
    private List<ListingAttributeDto> attributes = new ArrayList<>();


}