package com.trapuce.marketplace.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.trapuce.marketplace.dtos.listingdto.ListingAttributeDto;
import com.trapuce.marketplace.dtos.listingdto.ListingCreateDto;
import com.trapuce.marketplace.dtos.listingdto.ListingResponseDto;
import com.trapuce.marketplace.dtos.listingdto.ListingUpdateDto;
import com.trapuce.marketplace.models.Listing;
import com.trapuce.marketplace.models.ListingAttribute;


@Mapper(componentModel = "spring")
public interface ListingMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true) 
    @Mapping(target = "images", ignore = true) 
    Listing listingCreateDtoToListing(ListingCreateDto listingCreateDto);

    ListingResponseDto listingToListingResponseDto(Listing listing);

    ListingAttribute  listingAttributeDtoToListingAttribute(ListingAttributeDto listingAttributeDto);



    @Mapping(target = "user", ignore = true) 
    @Mapping(target = "category", ignore = true) 
    @Mapping(target = "attributes", ignore = true) 
    @Mapping(target = "images", ignore = true)
    void updateListingFromDto(ListingUpdateDto dto, @MappingTarget Listing listing);
}
