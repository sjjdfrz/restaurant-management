package com.neshan.restaurantmanagement.model.dto;

import java.util.List;

public record CartDto(
        List<CartItemDto> cartItems
) {
}
