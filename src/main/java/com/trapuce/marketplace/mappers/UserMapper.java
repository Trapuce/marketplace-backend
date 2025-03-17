package com.trapuce.marketplace.mappers;

import org.mapstruct.Mapper;

import com.trapuce.marketplace.dtos.userDto.UserCreateDto;
import com.trapuce.marketplace.dtos.userDto.UserResponseDto;
import com.trapuce.marketplace.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    

    UserResponseDto userToUserResponseDto(User user);

    User userCreateDtoToUser(UserCreateDto userCreateDto);
}
