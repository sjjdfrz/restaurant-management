package com.neshan.restaurantmanagement.security;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record RegisterRequest(

        @NotBlank(message = "Invalid firstName: Empty firstname!")
        String firstName,

        @NotBlank(message = "Invalid lastName: Empty lastname!")
        String lastName,

        @NotBlank(message = "Invalid Email: Empty email!")
        @Email
        String email,

        @NotBlank(message = "Invalid Password: Empty password!")
        String password,

        @NotBlank(message = "Invalid ConfirmPassword: Empty confirmPassword!")
        String confirmPassword
) {
}
