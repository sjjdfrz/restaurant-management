package com.neshan.restuarantmanagement.repository;

import com.neshan.restuarantmanagement.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
