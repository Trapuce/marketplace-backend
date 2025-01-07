package com.trapuce.marketplace.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class VehicleAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

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

