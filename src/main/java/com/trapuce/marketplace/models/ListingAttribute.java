package com.trapuce.marketplace.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "listing_attributes")
@NoArgsConstructor
public class ListingAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;
    
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
    
    @Column
    private String value;


}