package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MenuItemDto(

        long id,

        @NotBlank(message = "Invalid Name: Empty name!")
        String name,

        @NotNull(message = "Invalid Price: Null price!")
        double price,

        String description

) {
}
