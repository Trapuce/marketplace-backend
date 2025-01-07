package com.trapuce.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;


    public User FindUserById(long id){

        return this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User ID."));
    }
    
}
