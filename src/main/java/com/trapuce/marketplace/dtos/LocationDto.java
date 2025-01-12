package com.trapuce.marketplace.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocationDto {

    @NotBlank(message = "City cannot be blank")
    @Size(max = 255, message = "City cannot exceed 255 characters")
    private String city;

    @NotBlank(message = "Postal code cannot be blank")
    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    private String postal_code;

    @NotBlank(message = "Department cannot be blank")
    @Size(max = 255, message = "Department cannot exceed 255 characters")
    private String department;

    @NotBlank(message = "Region cannot be blank")
    @Size(max = 255, message = "Region cannot exceed 255 characters")
    private String region;

    @NotNull(message = "Latitude cannot be null")
    @DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be between -90 and 90")
    private Float latitude;

    @NotNull(message = "Longitude cannot be null")
    @DecimalMin(value = "-180.0", inclusive = true, message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", inclusive = true, message = "Longitude must be between -180 and 180")
    private Float longitude;
}
