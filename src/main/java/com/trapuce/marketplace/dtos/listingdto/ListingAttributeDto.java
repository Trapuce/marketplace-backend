package com.trapuce.marketplace.dtos.listingdto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ListingAttributeDto {

    @NotNull(message = "L'attribut est obligatoire")
    private UUID attributeId;

    private String value;
    
}
