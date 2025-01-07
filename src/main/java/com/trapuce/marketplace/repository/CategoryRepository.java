package com.trapuce.marketplace.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trapuce.marketplace.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}