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
public class ElectronicAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    private String brand;
    private String model;
    private String condition; // NEW or USED
    private String capacity;
    private String color;
    private String operatingSystem;
    private String processor;
    private String ram;
    private String storage;
    private String screenResolution;
    private Boolean warranty;
}
