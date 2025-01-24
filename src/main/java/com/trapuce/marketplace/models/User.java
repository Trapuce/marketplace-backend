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
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    private boolean accountVerified;

    @Temporal(TemporalType.DATE)
    private Date registration_date;

    private Boolean is_professional;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Advertisement> advertisements;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_confirmation_token_id")
    private EmailConfirmationToken emailConfirmationToken;

    public void addAd(Advertisement advertisement) {
        if (advertisements == null) {
            advertisements = new ArrayList<>();
        }
        advertisements.add(advertisement);
        advertisement.setUser(this);
    }

    public void removeAd(Advertisement advertisement) {
        if (advertisements != null) {
            advertisements.remove(advertisement);
            advertisement.setUser(null);
        }
    }

}