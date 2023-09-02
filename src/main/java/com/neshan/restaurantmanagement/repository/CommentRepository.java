package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUserId(Long userId);
}
