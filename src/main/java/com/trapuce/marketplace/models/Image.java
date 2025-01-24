package com.trapuce.marketplace.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String type;
    @ManyToOne
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;
    @Lob
    private byte[] imageData;
    @OneToOne(mappedBy = "image")
    private User user;
}