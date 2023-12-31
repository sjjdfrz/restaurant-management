package com.neshan.restaurantmanagement.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
        String password,

        @NotBlank(message = "Invalid ConfirmPassword: Empty confirmPassword!")
        String confirmPassword
) {
}
