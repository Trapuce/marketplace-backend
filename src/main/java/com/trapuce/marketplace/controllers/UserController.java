package com.trapuce.marketplace.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trapuce.marketplace.dtos.userDto.UserResponseDto;
import com.trapuce.marketplace.dtos.userDto.UserUpdateDto;
import com.trapuce.marketplace.service.UserService;

import jakarta.validation.Valid;


@RequestMapping("api/users")
@RestController
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<com.trapuce.marketplace.dtos.userDto.UserResponseDto> getUser(@PathVariable UUID id){
        UserResponseDto userResponse = this.userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser( @RequestBody UserUpdateDto userUpdateDto,@PathVariable UUID id){
        return ResponseEntity.ok(this.userService.updateUser(userUpdateDto, id));
    }

 


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id){
        return ResponseEntity.ok(this.userService.deleteUser(id));
    }
}
