package com.neshan.restaurantmanagement.model.dto;

import com.neshan.restaurantmanagement.model.entity.CartItem;

import java.util.List;

public record CartDto(
        List<CartItemDto> cartItems
) {
}
