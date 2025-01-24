package com.trapuce.marketplace.dtos;

import java.util.Date;
import java.util.List;

import com.trapuce.marketplace.models.AdvertisementStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdvertisementPostDto {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Float price;

    @NotNull(message = "Publication date cannot be null")
    private Date publication_date;

    @NotNull(message = "Status cannot be null")
    private AdvertisementStatus status;

    @NotNull(message = "Urgency status cannot be null")
    private Boolean is_urgent;

    @NotNull(message = "Activity status cannot be null")
    private Boolean is_active;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    private List<byte[]> photos;

    @Valid
    private LocationDto location;

    @Valid
    private RealEstateAttributesDto realEstateAttributes;

    @Valid
    private VehicleAttributesDto vehicleAttributes;

    @Valid
    private ElectronicAttributesDto electronicAttributes;

    @Valid
    private ProfessionalEquipmentAttributesDto professionalEquipmentAttributesDto;

}