package com.trapuce.marketplace.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String city;
    private String postal_code;
    private String department;
    private String region;
    private Float latitude;
    private Float longitude;
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Ad> ads;

    public void addAd(Ad ad) {
        if (ads == null) {
            ads = new ArrayList<>(); 
        }
        if (ad != null && !ads.contains(ad)) { 
            ads.add(ad);
            ad.setLocation(this); 
        }
    }

    public void removeAd(Ad ad) {
        if (ads != null && ad != null && ads.contains(ad)) { 
            ads.remove(ad);
            ad.setLocation(null); 
        }
    }

}
