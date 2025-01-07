package com.trapuce.marketplace.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trapuce.marketplace.dtos.LoginUserDto;
import com.trapuce.marketplace.dtos.RegisterUserDto;
import com.trapuce.marketplace.models.User;



@Mapper(componentModel = "spring")
public interface UserMapper {

 
    User RegisterUserDtoToUser(RegisterUserDto registerUserDto);

    User loginUserDtotoUser(LoginUserDto loginUserDto);

}