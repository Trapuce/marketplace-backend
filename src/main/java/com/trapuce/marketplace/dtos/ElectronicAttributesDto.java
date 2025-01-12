package com.trapuce.marketplace.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ElectronicAttributesDto {

    @NotNull(message = "Brand is required")
    @Size(min = 1, max = 100, message = "Brand should be between 1 and 100 characters")
    private String brand;

    @NotNull(message = "Model is required")
    @Size(min = 1, max = 100, message = "Model should be between 1 and 100 characters")
    private String model;

    @NotNull(message = "Condition is required")
    @Size(min = 1, max = 20, message = "Condition should be between 1 and 20 characters")
    private String condition; 

    @NotNull(message = "Capacity is required")
    @Size(min = 1, max = 50, message = "Capacity should be between 1 and 50 characters")
    private String capacity;

    @NotNull(message = "Color is required")
    @Size(min = 1, max = 30, message = "Color should be between 1 and 30 characters")
    private String color;

    @NotNull(message = "Operating System is required")
    @Size(min = 1, max = 50, message = "Operating System should be between 1 and 50 characters")
    private String operatingSystem;

    @NotNull(message = "Processor is required")
    @Size(min = 1, max = 100, message = "Processor should be between 1 and 100 characters")
    private String processor;

    @NotNull(message = "RAM is required")
    @Size(min = 1, max = 50, message = "RAM should be between 1 and 50 characters")
    private String ram;

    @NotNull(message = "Storage is required")
    @Size(min = 1, max = 50, message = "Storage should be between 1 and 50 characters")
    private String storage;

    @NotNull(message = "Screen Resolution is required")
    @Size(min = 1, max = 50, message = "Screen Resolution should be between 1 and 50 characters")
    private String screenResolution;

    @NotNull(message = "Warranty is required")
    private Boolean warranty;
}
