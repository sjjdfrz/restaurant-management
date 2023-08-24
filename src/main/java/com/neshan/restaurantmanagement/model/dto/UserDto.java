package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restaurantmanagement.model.entity.Order;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(

        long id,

        @NotBlank(message = "Invalid firstName: Empty firstname!")
        String firstName,

        @NotBlank(message = "Invalid lastName: Empty lastname!")
        String lastName,

        @NotBlank(message = "Invalid Email: Empty email!")
        @Email
        String email,

        List<Order> orders
) {
}
