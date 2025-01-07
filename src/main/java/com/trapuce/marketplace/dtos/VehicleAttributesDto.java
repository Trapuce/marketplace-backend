package com.trapuce.marketplace.dtos;

import lombok.Data;

@Data
public class VehicleAttributesDto {
    private String registrationNumber;
    private String brand;
    private String model;
    private Integer year;
    private Integer mileage;
    private String fuelType;
    private String gearbox;
    private Integer fiscalPower;
    private Boolean isFirstHand;
    private String technicalControl;
    private String vehicleType;
}
