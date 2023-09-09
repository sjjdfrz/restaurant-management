package com.neshan.restaurantmanagement.controller;


import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.service.AuthService;
import com.neshan.restaurantmanagement.model.dto.LoginRequest;
import com.neshan.restaurantmanagement.model.dto.RegisterRequest;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @RateLimiter(name = "rate-limit")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @RateLimiter(name = "login")
    public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
