package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDto(

        long id,

        @NotBlank(message = "Invalid Title: Empty title!")
        String title,

        List<ItemDto> items
) {
}
