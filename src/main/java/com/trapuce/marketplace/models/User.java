package com.trapuce.marketplace.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "profile_picture_url", length = 255)
    private String profilePictureUrl;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column
    private String address;

    @Column
    private String city   ;


    @Column(name = "postal_code")
    private int postalCode;

    @Column
    private boolean isProfessional;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "registration_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(name = "last_connection_date")
    private LocalDateTime lastConnectionDate;

    @OneToMany(mappedBy = "user")
    private List<Listing> listings = new ArrayList<>();
    public void addListing(Listing listing) {
        listings.add(listing);
        listing.setUser(this);
    }   
     public void removeListing(Listing listing) {
        listings.remove(listing);
        listing.setUser(null);
    }




}
