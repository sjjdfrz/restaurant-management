package com.neshan.restaurantmanagement.model.dto;

public record CartItemDto(
        Integer quantity,
        ItemDto item
) {
}
