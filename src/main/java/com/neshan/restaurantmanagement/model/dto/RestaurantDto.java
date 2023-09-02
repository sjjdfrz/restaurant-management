package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestaurantDto(

        long id,

        @NotBlank(message = "Invalid Name: Empty name!")
        String name,

        @NotNull(message = "Invalid Telephone: Empty telephone!")
        Long telephone,

        @NotBlank(message = "Invalid Address: Empty address!")
        String address,

        List<CategoryDto> categories
) {

}
