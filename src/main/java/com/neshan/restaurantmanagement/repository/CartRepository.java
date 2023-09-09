package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserIdAndId(long userId, long id);
    List<Cart> findAllByUserId(long userId);
}
