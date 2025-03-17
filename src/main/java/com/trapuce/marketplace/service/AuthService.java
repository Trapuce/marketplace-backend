package com.trapuce.marketplace.service;

import org.springframework.stereotype.Service;

import com.trapuce.marketplace.dtos.userDto.LoginRequestDto;
import com.trapuce.marketplace.dtos.userDto.UserCreateDto;
import com.trapuce.marketplace.dtos.userDto.UserResponseDto;
import com.trapuce.marketplace.mappers.UserMapper;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;


    private final UserMapper userMapper;


    public AuthService(UserRepository userRepository,  UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public UserResponseDto registerUser(UserCreateDto userCreateDto) {
         User user = this.userMapper.userCreateDtoToUser(userCreateDto);
        // user.setPasswordHash(this.passwordEncoder.encode(userCreateDto.getPassword()));
        return this.userMapper.userToUserResponseDto(this.userRepository.save(user));
    }


    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        User user = this.userRepository.findByEmail(loginRequestDto.getEmail());
        if (user != null && loginRequestDto.getPassword().equals(user.getPasswordHash())) {
            UserResponseDto userResponseDto = this.userMapper.userToUserResponseDto(user);
            return  userResponseDto;
        }
        return null;
    }
    
}
