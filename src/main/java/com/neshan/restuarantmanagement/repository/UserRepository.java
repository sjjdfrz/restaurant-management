package com.neshan.restuarantmanagement.repository;

import com.neshan.restuarantmanagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
