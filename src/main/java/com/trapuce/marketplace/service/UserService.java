package com.trapuce.marketplace.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trapuce.marketplace.dtos.userDto.UserCreateDto;
import com.trapuce.marketplace.dtos.userDto.UserResponseDto;
import com.trapuce.marketplace.dtos.userDto.UserUpdateDto;
import com.trapuce.marketplace.mappers.UserMapper;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;


    private final UserMapper userMapper;


    public UserService(UserRepository userRepository,  UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }







    public UserResponseDto getUserById(UUID id) {
        return this.userMapper.userToUserResponseDto(this.userRepository.findById(id).orElse(null));
    }


    public UserResponseDto getUserByEmail(String email) {
        return this.userMapper.userToUserResponseDto(this.userRepository.findByEmail(email));
    }



    public List<UserResponseDto> getAllUsers(){
        return this.userRepository.findAll().stream()
                .map(userMapper::userToUserResponseDto)
                .collect(Collectors.toList());
    }


    public UserResponseDto updateUser(UserUpdateDto userUpdateDto, UUID id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return null ;
    }


    public Boolean deleteUser(UUID id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    
}
