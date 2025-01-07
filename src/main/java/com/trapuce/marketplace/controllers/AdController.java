package com.trapuce.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trapuce.marketplace.dtos.AdPostDto;
import com.trapuce.marketplace.models.Ad;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.service.AdService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ads")
public class AdController {

    @Autowired
    public AdService adService;

    @PostMapping("/new")
    public ResponseEntity<Ad> createAd(@Valid @RequestBody AdPostDto adPostDto) {
        Ad createdAd = adService.createAd(adPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }
}
