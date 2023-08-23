package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
