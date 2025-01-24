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
    private List<Advertisement> advertisements;

    public void addAdvertisement(Advertisement advertisement) {
        if (advertisements == null) {
            advertisements = new ArrayList<>(); 
        }
        if (advertisement != null && !advertisements.contains(advertisement)) { 
            advertisements.add(advertisement);
            advertisement.setLocation(this); 
        }
    }

    public void removeAdvertisement(Advertisement advertisement) {
        if (advertisements != null && advertisement != null && advertisements.contains(advertisement)) { 
            advertisements.remove(advertisement);
            advertisement.setLocation(null); 
        }
    }

}
