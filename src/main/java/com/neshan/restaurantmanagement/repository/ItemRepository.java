package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
