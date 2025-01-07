package com.trapuce.marketplace.mappers;

import org.mapstruct.Mapper;

import com.trapuce.marketplace.dtos.VehicleAttributesDto;
import com.trapuce.marketplace.models.VehicleAttributes;

@Mapper(componentModel = "spring")
public interface VehicleAttributesMapper {

    VehicleAttributes vehicleAttributesDtoToVehicleAttributes(VehicleAttributesDto vehicleAttributesDto);

    VehicleAttributesDto vehicleAttributesToVehicleAttributesDto(VehicleAttributes vehicleAttributes);
    
}
