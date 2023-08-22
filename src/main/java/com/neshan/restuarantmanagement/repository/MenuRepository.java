package com.neshan.restuarantmanagement.repository;

import com.neshan.restuarantmanagement.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
