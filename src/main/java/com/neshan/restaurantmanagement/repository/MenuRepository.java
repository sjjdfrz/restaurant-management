package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
