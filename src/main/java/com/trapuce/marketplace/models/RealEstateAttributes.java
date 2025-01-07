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
public class RealEstateAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    private Float habitableSurface;
    private Integer numberOfRooms;
    private Integer numberOfBedrooms;
    private Integer numberOfBathrooms;
    private String propertyType;
    private Integer floor;
    private Boolean hasElevator;
    private String energyPerformance;
    private String heatingType;
    private Boolean furnished;
    private Float charges;
    private String transactionType; 
}
