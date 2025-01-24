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
    private List<Advertisement> advertisements;

    public void addAdvertisement(Advertisement advertisement) {
        if (advertisements == null) {
            advertisements = new ArrayList<>(); 
        }
        if (advertisement != null && !advertisements.contains(advertisement)) { 
            advertisements.add(advertisement);
            advertisement.setCategory(this); 
        }
    }

    public void removeAdvertisement(Advertisement advertisement) {
        if (advertisements != null && advertisement != null && advertisements.contains(advertisement)) {
            advertisements.remove(advertisement);
            advertisement.setCategory(null); 
        }
    }

}
