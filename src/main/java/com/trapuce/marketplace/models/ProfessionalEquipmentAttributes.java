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
public class ProfessionalEquipmentAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    private String equipmentType;
    private String brand;
    private String model;
    private String condition; // NEW or USED
    private Integer year;
    private String certification;
    private Boolean warranty;
    private String usage;
}
