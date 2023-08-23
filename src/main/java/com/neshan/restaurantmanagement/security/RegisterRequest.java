package com.neshan.restaurantmanagement.security;


import lombok.*;

@Builder
public record RegisterRequest(

        String firstName,
        String lastName,
        String email,
        String password
) {
}
