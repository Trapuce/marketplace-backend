package com.trapuce.marketplace.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "advertisements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Float price;

    @Temporal(TemporalType.DATE)
    private Date publication_date;

    @Enumerated(EnumType.STRING)
    private AdvertisementStatus status;

    private Boolean is_urgent;
    private Boolean is_active;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private RealEstateAttributes realEstateAttributes;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private VehicleAttributes vehicleAttributes;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private ElectronicAttributes electronicAttributes;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private ProfessionalEquipmentAttributes professionalEquipmentAttributes;

    public void addMessage(Message message) {
        messages.add(message);
        message.setAdvertisement(this);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
        message.setAdvertisement(null);
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
        favorite.setAdvertisement(this);
    }

    public void removeFavorite(Favorite favorite) {
        favorites.remove(favorite);
        favorite.setAdvertisement(null);
    }

    public void addImage(Image image) {
        images.add(image);
        image.setAdvertisement(this);
    }

    public void removePhoto(Image image) {
        images.remove(image);
        image.setAdvertisement(null);
    }
}