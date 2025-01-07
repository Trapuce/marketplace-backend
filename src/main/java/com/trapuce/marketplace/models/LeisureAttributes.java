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
public class LeisureAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    private String leisureType;
    private String condition; // NEW or USED
    private String brand;
    private String targetAudience;
    private String level; // BEGINNER, INTERMEDIATE, ADVANCED
    private String period; // SUMMER, WINTER, YEAR_ROUND
}
