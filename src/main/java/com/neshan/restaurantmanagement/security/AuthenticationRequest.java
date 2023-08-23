package com.neshan.restaurantmanagement.security;

import lombok.*;

@Builder
public record AuthenticationRequest(String email, String password) {
}
