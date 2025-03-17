package com.trapuce.marketplace.service;

import com.trapuce.marketplace.models.Attribute;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trapuce.marketplace.dtos.listingdto.ListingAttributeDto;
import com.trapuce.marketplace.dtos.listingdto.ListingAttributeUpdateDto;
import com.trapuce.marketplace.dtos.listingdto.ListingCreateDto;
import com.trapuce.marketplace.dtos.listingdto.ListingResponseDto;
import com.trapuce.marketplace.dtos.listingdto.ListingUpdateDto;
import com.trapuce.marketplace.mappers.ListingMapper;
import com.trapuce.marketplace.models.Category;
import com.trapuce.marketplace.models.Listing;
import com.trapuce.marketplace.models.ListingAttribute;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.repository.AttributeRepository;
import com.trapuce.marketplace.repository.CategoryRepository;
import com.trapuce.marketplace.repository.ListingAttributeRepository;
import com.trapuce.marketplace.repository.ListingRepository;
import com.trapuce.marketplace.repository.UserRepository;

@Service
public class ListingService {

    private final ListingRepository listingRepository;

    private final UserRepository userRepository;

    private final ListingMapper listingMapper;

    private final CategoryRepository categoryRepository;

    private final AttributeRepository attributeRepository;

    private final ListingAttributeRepository listingAttributeRepository;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository,
            ListingMapper listingMapper, CategoryRepository categoryRepository, AttributeRepository attributeRepository,
            ListingAttributeRepository listingAttributeRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.listingMapper = listingMapper;
        this.categoryRepository = categoryRepository;
        this.attributeRepository = attributeRepository;
        this.listingAttributeRepository = listingAttributeRepository;
    }

    public ListingResponseDto createListing(ListingCreateDto listingCreateDto) {

        Listing listing = this.listingMapper.listingCreateDtoToListing(listingCreateDto);

        User user = userRepository.findById(listingCreateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Category category = categoryRepository.findById(listingCreateDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        user.addListing(listing);
        listing.setCategory(category);

        Listing savedListing = listingRepository.save(listing);

        for (ListingAttributeDto listingAttributeDto : listingCreateDto.getAttributes()) {

            Attribute attribute = this.attributeRepository.findById(listingAttributeDto.getAttributeId())
                    .orElseThrow(() -> new RuntimeException("Attribut non trouvé"));

            System.out.println("Attribute: " + attribute.getName());

            ListingAttribute listingAttribute = new ListingAttribute();
            listingAttribute.setListing(savedListing);
            listingAttribute.setAttribute(attribute);
            listingAttribute.setValue(listingAttributeDto.getValue());

            listingAttributeRepository.save(listingAttribute);
        }

        return listingMapper.listingToListingResponseDto(savedListing);

    }

    public List<ListingResponseDto> getAllListings() {
        return this.listingRepository.findAll().stream().map(listingMapper::listingToListingResponseDto)
                .collect(Collectors.toList());
    }

    public ListingResponseDto getListingById(UUID id) {
        return this.listingMapper.listingToListingResponseDto(this.listingRepository.findById(id).orElse(null));
    }

    public List<ListingResponseDto> getListingsByUserId(UUID userId) {
        return this.listingRepository.findByUserId(userId).stream()
                .map(listing -> listingMapper.listingToListingResponseDto(listing))
                .collect(Collectors.toList());
    }

    public List<ListingResponseDto> getListingsByCategoryId(UUID categoryId) {
        return this.listingRepository.findByCategoryId(categoryId).stream()
                .map(listing -> listingMapper.listingToListingResponseDto(listing))
                .collect(Collectors.toList());
    }

    public ListingResponseDto updateListing(ListingUpdateDto listingUpdateDto, UUID listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing non trouvé"));

        this.listingMapper.updateListingFromDto(listingUpdateDto, listing);

        if (listingUpdateDto.getAttributes() != null) {
            for (ListingAttributeUpdateDto attributeDto : listingUpdateDto.getAttributes()) {
                ListingAttribute listingAttribute = listingAttributeRepository
                        .findByListingAndAttributeId(listing, attributeDto.getAttributeId())
                        .orElseThrow(() -> new RuntimeException("Attribut non trouvé pour ce listing"));

                listingAttribute.setValue(attributeDto.getValue());
                listingAttributeRepository.save(listingAttribute);
            }
        }

        Listing updatedListing = listingRepository.save(listing);

        return listingMapper.listingToListingResponseDto(updatedListing);

    }

    public Boolean deleteListing(UUID id) {
        if (this.listingRepository.existsById(id)) {
            this.listingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
