package com.neshan.restaurantmanagement.model.dto;

public record CartItemRequestDto(

        Integer quantity,
        long itemId
) {
}
