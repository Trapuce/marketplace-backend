package com.trapuce.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trapuce.marketplace.models.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long>{
    
}
