package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
