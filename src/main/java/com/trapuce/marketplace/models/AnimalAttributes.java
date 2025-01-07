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
public class AnimalAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    private String species;
    private String breed;
    private String age;
    private String gender; // MALE or FEMALE
    private Boolean vaccinated;
    private Boolean pedigree;
    private String diet;
    private String behavior;
}
