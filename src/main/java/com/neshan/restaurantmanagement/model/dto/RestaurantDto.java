package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restaurantmanagement.model.entity.Order;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestaurantDto(

        long id,

        @NotBlank(message = "Invalid Name: Empty name!")
        String name,

        long telephone,
        String address,
        List<MenuDto> menus,
        List<Order> orders
) {
}
