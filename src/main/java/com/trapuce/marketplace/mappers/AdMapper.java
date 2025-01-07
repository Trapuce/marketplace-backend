package com.trapuce.marketplace.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trapuce.marketplace.dtos.AdPostDto;
import com.trapuce.marketplace.models.Ad;
import com.trapuce.marketplace.repository.CategoryRepository;
import com.trapuce.marketplace.repository.UserRepository;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "user", expression = "java(userRepository.findById(adPostDto.getUserId()).orElseThrow(() -> new RuntimeException(\"User not found\")))")
    @Mapping(target = "category", expression = "java(categoryRepository.findById(adPostDto.getCategoryId()).orElseThrow(() -> new RuntimeException(\"Category not found\")))")
    @Mapping(target = "id", ignore = true)
    Ad AdPostDtoToAd(AdPostDto adPostDto, @Context UserRepository userRepository,
            @Context CategoryRepository categoryRepository);

}
