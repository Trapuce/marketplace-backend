package com.trapuce.marketplace.dtos.listingdto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ListingUpdateDto {
    
    @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères")
    private String title;

    @Size(min = 20, message = "La description doit contenir au moins 20 caractères")
    private String description;

    private BigDecimal price;

    private Boolean isPriceNegotiable;

    private String conditionState;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private List<String> images; 

    private List<ListingAttributeUpdateDto> attributes; 
}


