package com.trapuce.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trapuce.marketplace.models.Photo;

public interface PhotoRepository  extends JpaRepository <Photo , Long>{
    
}
