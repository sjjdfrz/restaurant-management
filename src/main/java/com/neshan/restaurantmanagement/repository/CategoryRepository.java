package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
