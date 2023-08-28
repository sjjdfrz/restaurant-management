package com.neshan.restaurantmanagement.security;


import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @RateLimiter(name = "rate-limit")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    @RateLimiter(name = "login")
    public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PatchMapping("/updateMe")
    @RateLimiter(name = "rate-limit")
    public ResponseEntity<ApiResponse<Object>> updateMe(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.update(request));
    }

    @DeleteMapping("/deleteMe")
    @RateLimiter(name = "rate-limit")
    public ResponseEntity<ApiResponse<Object>> deleteMe() {
        return ResponseEntity.ok(authenticationService.delete());
    }

    @GetMapping("/me")
    @RateLimiter(name = "rate-limit")
    public ResponseEntity<ApiResponse<UserDto>> getMe() {
        return ResponseEntity.ok(authenticationService.getMe());
    }
}
