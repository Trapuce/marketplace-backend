package com.trapuce.marketplace.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trapuce.marketplace.dtos.LoginUserDto;
import com.trapuce.marketplace.dtos.RegisterUserDto;
import com.trapuce.marketplace.models.User;



@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registration_date", expression = "java(new java.util.Date())")
    @Mapping(target = "is_professional", constant = "false")
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "sent_messages", ignore = true)
    @Mapping(target = "received_messages", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    @Mapping(target = "given_evaluations", ignore = true)
    @Mapping(target = "received_evaluations", ignore = true)
    User RegisterUserDtoToUser(RegisterUserDto registerUserDto);

    User loginUserDtotoUser(LoginUserDto loginUserDto);

}