package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
}
