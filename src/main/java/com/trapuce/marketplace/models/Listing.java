package com.trapuce.marketplace.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "is_price_negotiable", nullable = true, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPriceNegotiable = false;

    @Column(name = "condition_state", length = 50)
    private ConditionState conditionState;

    @Column(name = "publication_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime publicationDate = LocalDateTime.now();

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "is_urgent", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isUrgent = false;

    @Column(name = "is_highlighted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isHighlighted = false;

    @Column(name = "is_sold", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isSold = false;

    @Column(name = "view_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListingImage> images;

    public void addImage(ListingImage image) {
        images.add(image);
        image.setListing(this);
    }
    public void removeImage(ListingImage image) {
        images.remove(image);
        image.setListing(null);
    }


    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListingAttribute> attributes;

    public void addAttribute(ListingAttribute attribute) {
        attributes.add(attribute);
        attribute.setListing(this);
    }
    public void removeAttribute(ListingAttribute attribute) {
        attributes.remove(attribute);
        attribute.setListing(null);
    }
}