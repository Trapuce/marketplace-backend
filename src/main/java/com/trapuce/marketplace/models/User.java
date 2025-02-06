package com.trapuce.marketplace.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column
    private String address;

    @Column(nullable = false)
    private boolean accountVerified;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Advertisement> advertisements;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "evaluator", cascade = CascadeType.ALL)
    private List<Evaluation> givenEvaluations;

    @OneToMany(mappedBy = "evaluated", cascade = CascadeType.ALL)
    private List<Evaluation> receivedEvaluations;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profilePicture_id", referencedColumnName = "id")
    private Image profilePicture;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Token emailConfirmationToken;

    public void addAdvertisement(Advertisement advertisement) {
        if (advertisements == null) {
            advertisements = new ArrayList<>();
        }
        advertisements.add(advertisement);
        advertisement.setUser(this);
    }

    public void removeAdvertisement(Advertisement advertisement) {
        if (advertisements != null) {
            advertisements.remove(advertisement);
            advertisement.setUser(null);
        }
    }

}