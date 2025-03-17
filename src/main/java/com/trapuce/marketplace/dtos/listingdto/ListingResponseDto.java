package com.trapuce.marketplace.dtos.listingdto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trapuce.marketplace.dtos.categoryDto.CategoryResponseDto;
import com.trapuce.marketplace.dtos.userDto.UserResponseDto;

import lombok.Data;


@Data
public class ListingResponseDto {
    
     private UUID id;

    private String title;

    private String description;

    private BigDecimal price;

    private Boolean isPriceNegotiable;

    private String conditionState;

    private LocalDateTime publicationDate;

    private LocalDateTime expirationDate;

    private Boolean isUrgent;

    private Boolean isHighlighted;

    private Boolean isSold;

    private Integer viewCount;

    private UserResponseDto user;

    private CategoryResponseDto category;

    private List<String> imageUrls; 

    private BigDecimal latitude;

    private BigDecimal longitude;
}
