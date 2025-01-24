package com.trapuce.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trapuce.marketplace.dtos.AdvertisementPostDto;
import com.trapuce.marketplace.models.Advertisement;

import com.trapuce.marketplace.service.AdvertisementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ads")
public class AdvertisementController {

    @Autowired
    public AdvertisementService advertisementService;

    @PostMapping("/users/{userId}/ads")
    public ResponseEntity<Advertisement> createUserAd(
            @PathVariable Long userId,
            @Valid @RequestBody AdvertisementPostDto adPostDto) {
        Advertisement createdAd = advertisementService.createAdForUser(userId, adPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }
}
