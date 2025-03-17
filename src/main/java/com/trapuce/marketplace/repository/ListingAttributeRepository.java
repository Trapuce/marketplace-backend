package com.trapuce.marketplace.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trapuce.marketplace.models.Listing;
import com.trapuce.marketplace.models.ListingAttribute;
import com.trapuce.marketplace.models.User;

public interface ListingAttributeRepository extends JpaRepository<ListingAttribute , UUID> {

    Optional<ListingAttribute> findByListingAndAttributeId(Listing listing, UUID attributeId);
    
}
