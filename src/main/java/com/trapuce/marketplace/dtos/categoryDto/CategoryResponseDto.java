package com.trapuce.marketplace.dtos.categoryDto;

import java.util.UUID;

import lombok.Data;

@Data
public class CategoryResponseDto {

    private UUID id;

    private String name;


    private String description;
}
