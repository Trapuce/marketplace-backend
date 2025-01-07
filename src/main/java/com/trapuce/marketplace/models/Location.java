package com.trapuce.marketplace.models;

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
}
