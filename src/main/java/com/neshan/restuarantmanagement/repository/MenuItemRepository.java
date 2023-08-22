package com.neshan.restuarantmanagement.repository;

import com.neshan.restuarantmanagement.model.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
