package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restaurantmanagement.model.entity.OrderItem;
import com.neshan.restaurantmanagement.model.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDto(

        long id,
        Date orderDate,
        double totalCost,

        @NotNull(message = "orderItems is empty!")
        List<OrderItem> orderItems,

        User user
) {
}
