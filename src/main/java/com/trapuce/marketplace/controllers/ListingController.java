package com.trapuce.marketplace.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trapuce.marketplace.dtos.listingdto.ListingCreateDto;
import com.trapuce.marketplace.dtos.listingdto.ListingResponseDto;
import com.trapuce.marketplace.dtos.listingdto.ListingUpdateDto;
import com.trapuce.marketplace.service.ListingService;

import jakarta.validation.Valid;

@RequestMapping("api/listings")
@RestController
public class ListingController {
    
    private final ListingService listingService;


    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }



    @PostMapping("/create")
    public ResponseEntity<ListingResponseDto> createListing(@Valid @RequestBody ListingCreateDto listingCreateDto){
        return ResponseEntity.ok(listingService.createListing(listingCreateDto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ListingResponseDto> updateListing(@Valid @RequestBody ListingUpdateDto listingUpdateDto , @PathVariable UUID id){
        return ResponseEntity.ok(listingService.updateListing(listingUpdateDto , id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDto> getListing(@PathVariable UUID id){
        return ResponseEntity.ok(listingService.getListingById(id));
    }

    @GetMapping
    public ResponseEntity<List<ListingResponseDto>> getAllListings(){
        return ResponseEntity.ok(listingService.getAllListings());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ListingResponseDto>> getListingsByCategory(@PathVariable UUID categoryId){
        return ResponseEntity.ok(listingService.getListingsByCategoryId(categoryId));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListingResponseDto>> getListingsByUser(@PathVariable UUID userId){
        return ResponseEntity.ok(listingService.getListingsByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteListing(@PathVariable UUID id){
            return ResponseEntity.ok(listingService.deleteListing(id));
    }
}
