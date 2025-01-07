package com.trapuce.marketplace.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trapuce.marketplace.models.Category;
import com.trapuce.marketplace.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    public CategoryRepository categoryRepository ;
    public Category findCategoryById(long id){

       return this.categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category ID."));
    }



}
