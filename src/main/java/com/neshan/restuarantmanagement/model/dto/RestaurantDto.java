package com.neshan.restuarantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestaurantDto(

        long id,

        @NotBlank(message = "Invalid Name: Empty name!")
        String name,

        long telephone,
        String address,
        Set<MenuDto> menus
) {
}
