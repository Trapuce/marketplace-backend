package com.trapuce.marketplace.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import lombok.Data;

@Data
public class VehicleAttributesDto {

    @NotBlank(message = "Registration number cannot be blank")
    @Size(max = 50, message = "Registration number cannot exceed 50 characters")
    private String registrationNumber;

    @NotBlank(message = "Brand cannot be blank")
    @Size(max = 100, message = "Brand cannot exceed 100 characters")
    private String brand;

    @NotBlank(message = "Model cannot be blank")
    @Size(max = 100, message = "Model cannot exceed 100 characters")
    private String model;

    @NotNull(message = "Year cannot be null")
    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    @Max(value = 2100, message = "Year must be less than or equal to 2100")
    private Integer year;

    @NotNull(message = "Mileage cannot be null")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    @NotBlank(message = "Fuel type cannot be blank")
    @Size(max = 50, message = "Fuel type cannot exceed 50 characters")
    private String fuelType;

    @NotBlank(message = "Gearbox cannot be blank")
    @Size(max = 50, message = "Gearbox cannot exceed 50 characters")
    private String gearbox;

    @NotNull(message = "Fiscal power cannot be null")
    @Min(value = 0, message = "Fiscal power must be a positive number")
    private Integer fiscalPower;

    @NotNull(message = "First hand status cannot be null")
    private Boolean isFirstHand;

    @NotBlank(message = "Technical control status cannot be blank")
    @Size(max = 100, message = "Technical control status cannot exceed 100 characters")
    private String technicalControl;

    @NotBlank(message = "Vehicle type cannot be blank")
    @Size(max = 50, message = "Vehicle type cannot exceed 50 characters")
    private String vehicleType;
}

