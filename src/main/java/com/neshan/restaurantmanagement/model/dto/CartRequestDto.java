package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restaurantmanagement.validation.Available;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartRequestDto(

        @Available
        List<CartItemRequestDto> cartItems
) {
}
