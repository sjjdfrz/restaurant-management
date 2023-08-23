package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restaurantmanagement.model.entity.MenuItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemDto(

        long id,

        @Min(1)
        int quantity,

        @NotNull(message = "menuItem is null!")
        MenuItem menuItem
) {
}
