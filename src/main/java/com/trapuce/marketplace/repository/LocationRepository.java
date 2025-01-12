package com.trapuce.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trapuce.marketplace.models.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    
}
