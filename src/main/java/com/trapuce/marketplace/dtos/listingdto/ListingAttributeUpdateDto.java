package com.trapuce.marketplace.dtos.listingdto;

import java.util.UUID;

import lombok.Data;

@Data
public class ListingAttributeUpdateDto {
    private UUID attributeId; 
    private String value; 
}