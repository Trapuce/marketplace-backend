package com.trapuce.marketplace.dtos;

import java.util.Date;

import com.trapuce.marketplace.models.AdStatus;

import com.trapuce.marketplace.models.Location;


import lombok.Data;

@Data
public class AdPostDto {

    private Long userId;
    private String title;
    private String description;
    private Float price;
    private Date publication_date;
    private AdStatus status;
    private Boolean is_urgent;
    private Boolean is_active;
    private Long categoryId;
    private Location location;

    private RealEstateAttributesDto realEstateAttributes;

    private VehicleAttributesDto vehicleAttributes;

    private ElectronicAttributesDto electronicAttributes;

}
