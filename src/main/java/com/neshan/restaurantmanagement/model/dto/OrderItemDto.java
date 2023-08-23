package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restaurantmanagement.model.entity.MenuItem;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemDto(

        long id,

        int quantity,
        MenuItem menuItem
) {
}
