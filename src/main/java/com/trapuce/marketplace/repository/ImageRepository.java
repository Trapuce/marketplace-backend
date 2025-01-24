package com.trapuce.marketplace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trapuce.marketplace.models.Image;


@Repository
public interface ImageRepository  extends JpaRepository <Image , Long>{
    Optional<Image> findByName(String name);
}
