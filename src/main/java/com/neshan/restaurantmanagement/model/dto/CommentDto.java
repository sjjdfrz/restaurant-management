package com.neshan.restaurantmanagement.model.dto;

import com.neshan.restaurantmanagement.model.entity.User;
import jakarta.validation.constraints.NotBlank;

public record CommentDto(

        String body,
        int score,
        User user,

        @NotBlank
        String response
) {
}
