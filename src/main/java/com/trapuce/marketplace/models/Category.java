package com.trapuce.marketplace.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Ad> ads;

    public void addAd(Ad ad) {
        if (ads == null) {
            ads = new ArrayList<>(); 
        }
        if (ad != null && !ads.contains(ad)) { 
            ads.add(ad);
            ad.setCategory(this); 
        }
    }

    public void removeAd(Ad ad) {
        if (ads != null && ad != null && ads.contains(ad)) {
            ads.remove(ad);
            ad.setCategory(null); 
        }
    }

}
