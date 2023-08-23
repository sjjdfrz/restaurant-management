package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
