package com.trapuce.marketplace.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trapuce.marketplace.models.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute , UUID> {
    
    
}
