package com.trapuce.marketplace.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trapuce.marketplace.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    @Autowired
    private UserService userService ;

    
    @PostMapping("/{userId}/photo")
    public ResponseEntity<String> addPhotoToUser(@PathVariable Long userId, @RequestBody MultipartFile photo) throws IOException {
        userService.addPhotoToUser(userId, photo);
        return ResponseEntity.ok("Photo added successfully");
    }

    @PutMapping("/{userId}/photo")
    public ResponseEntity<String> updateUserPhoto(@PathVariable Long userId, @RequestParam MultipartFile photo) throws IOException {
        userService.updateUserPhoto(userId, photo);
        return ResponseEntity.ok("Photo updated successfully");
    }

    @GetMapping("/{userId}/photo")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable Long userId) {
        byte[] photo = userService.getUserPhoto(userId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo);
    }

    @DeleteMapping("/{userId}/photo")
    public ResponseEntity<String> deleteUserPhoto(@PathVariable Long userId) {
        userService.deleteUserPhoto(userId);
        return ResponseEntity.ok("Photo deleted successfully");
    }

}
