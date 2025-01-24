package com.trapuce.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trapuce.marketplace.models.Advertisement;


@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>{
    
}
