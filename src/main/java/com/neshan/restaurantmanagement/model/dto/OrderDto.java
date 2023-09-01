package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restaurantmanagement.model.OrderStatus;
import com.neshan.restaurantmanagement.model.entity.CartItem;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDto(

        long id,
        Double totalCost,
        OrderStatus orderStatus,
        String deliveryTime,
        List<CartItemDto> items
) {
}
