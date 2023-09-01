package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.Cart;
import com.neshan.restaurantmanagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
