package com.trapuce.marketplace.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trapuce.marketplace.dtos.userDto.LoginRequestDto;
import com.trapuce.marketplace.dtos.userDto.UserCreateDto;
import com.trapuce.marketplace.dtos.userDto.UserResponseDto;
import com.trapuce.marketplace.service.AuthService;

import jakarta.validation.Valid;

@RequestMapping("api/auth")
@RestController
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserCreateDto userCreateDto){
       
        return ResponseEntity.ok(authService.registerUser(userCreateDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
}
