package com.neshan.restaurantmanagement.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record LoginRequest(

        @NotBlank(message = "Invalid Email: Empty email!")
        @Email
        String email,

        @NotBlank(message = "Invalid Password: Empty password!")
        String password
) {
}
