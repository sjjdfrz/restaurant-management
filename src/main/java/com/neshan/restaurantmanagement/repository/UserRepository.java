package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
