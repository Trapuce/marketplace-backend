package com.trapuce.marketplace.mappers;

import org.mapstruct.Mapper;

import com.trapuce.marketplace.dtos.CreateUserDTO;
import com.trapuce.marketplace.dtos.LoginUserDto;
import com.trapuce.marketplace.dtos.UserDTO;
import com.trapuce.marketplace.models.User;



@Mapper(componentModel = "spring")
public interface UserMapper {

 

    User CreateUserDTOToUser(CreateUserDTO createUserDto);

    UserDTO UserToUserDTO(User user);

    User loginUserDtotoUser(LoginUserDto loginUserDto);

}