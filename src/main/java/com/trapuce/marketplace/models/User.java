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

    private String email;

    private String password;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    @Temporal(TemporalType.DATE)
    private Date registration_date;

    private Boolean is_professional;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ad> ads;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sent_messages;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Message> received_messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "evaluator", cascade = CascadeType.ALL)
    private List<Evaluation> given_evaluations;

    @OneToMany(mappedBy = "evaluated", cascade = CascadeType.ALL)
    private List<Evaluation> received_evaluations;

    public void addAd(Ad ad) {
        if (ads == null) {
            ads = new ArrayList<>();
        }
        ads.add(ad);
        ad.setUser(this); 
    }

    public void removeAd(Ad ad) {
        if (ads != null) {
            ads.remove(ad);
            ad.setUser(null); 
        }
    }

}