package com.trapuce.marketplace.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name ="ads")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private AdStatus status;

    private Boolean is_urgent;
    private Boolean is_active;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Photo> photos;
}