package com.trapuce.marketplace.service;

import java.io.IOException;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trapuce.marketplace.exceptions.UserNotFoundException;
import com.trapuce.marketplace.models.Image;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.repository.ImageRepository;
import com.trapuce.marketplace.repository.UserRepository;
import com.trapuce.marketplace.utils.ImageUtils;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ImageRepository imageRepository;

    public void addPhotoToUser(Long idUser, MultipartFile photo) throws IOException {

        User user = this.userRepository.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException("Image doesnt exist !!!");
        }
        Image image = Image.builder()
                .name(photo.getOriginalFilename())
                .type(photo.getContentType())
                .imageData(ImageUtils.compressImage(photo.getBytes()))
                .build();

        user.setImage(image);
        image.setUser(user);

        this.userRepository.save(user);

    }

    public void updateUserPhoto(Long userId, MultipartFile photo) throws IOException {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getImage() != null) {
            Image oldImage = user.getImage();
            imageRepository.delete(oldImage);
        }

        Image newImage = Image.builder()
                .name(photo.getOriginalFilename())
                .type(photo.getContentType())
                .imageData(ImageUtils.compressImage(photo.getBytes()))
                .build();

        user.setImage(newImage);
        userRepository.save(user);
    }

    public byte[] getUserPhoto(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getImage() == null) {
            throw new IllegalArgumentException("User does not have a photo");
        }

        try {
            return ImageUtils.decompressImage(user.getImage().getImageData());
        } catch (DataFormatException | IOException e) {
            throw new RuntimeException("Error decompressing the image", e);
        }
    }

    public void deleteUserPhoto(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getImage() != null) {
            Image imageToDelete = user.getImage();
            user.setImage(null);
            imageRepository.delete(imageToDelete);
        } else {
            throw new IllegalArgumentException("User does not have a photo to delete");
        }

        userRepository.save(user);
    }

    // public void deleteUser(Long userId){
    //     User user = this.userRepository.findById(userId)
    //     .orElseThrow(() -> new UserNotFoundException("User not found"));

    //     if(user.getImage() !=null){
    //         Image imageToDelete = user.getImage();
    //         user.setImage(null);
    //     }
    // }

}
