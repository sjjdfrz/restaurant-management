package com.neshan.restaurantmanagement.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(

        @NotBlank
        String body,

        @Min(1)
        @Max(5)
        int score
) {
}
