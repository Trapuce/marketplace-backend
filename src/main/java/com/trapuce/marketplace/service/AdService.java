package com.trapuce.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trapuce.marketplace.dtos.AdPostDto;
import com.trapuce.marketplace.mappers.AdMapper;
import com.trapuce.marketplace.models.Ad;
import com.trapuce.marketplace.models.Category;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.repository.AdRepository;
import com.trapuce.marketplace.repository.CategoryRepository;
import com.trapuce.marketplace.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdService {

    @Autowired
    public AdRepository adRepository ;
    
    @Autowired
    public AdMapper adMapper ;

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public UserRepository userRepository;

    @Transactional
    public Ad createAd(AdPostDto adPostDto) {
        try {
            Ad ad = adMapper.AdPostDtoToAd(adPostDto, userRepository, categoryRepository);
            return adRepository.save(ad);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("pas de resource");
        }
    }
    
    
}
