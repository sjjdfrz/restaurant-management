package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentDto(

        long id,
        String body,
        Integer score,
        String username,

        @NotBlank
        String response
) {
}
