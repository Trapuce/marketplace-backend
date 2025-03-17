package com.trapuce.marketplace.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trapuce.marketplace.models.Listing;
import com.trapuce.marketplace.models.User;

@Repository
public interface ListingRepository extends JpaRepository<Listing , UUID> {

    Optional<Listing> findByUserId(UUID userId);

    Optional<Listing> findByCategoryId(UUID categoryId);
    
}
