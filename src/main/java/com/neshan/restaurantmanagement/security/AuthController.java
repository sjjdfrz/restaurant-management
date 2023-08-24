package com.neshan.restaurantmanagement.security;


import com.neshan.restaurantmanagement.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PatchMapping("/updateMe")
    public ResponseEntity<ApiResponse<Object>> update(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.update(request));
    }

    @PostMapping("/deleteMe")
    public ResponseEntity<ApiResponse<Object>> delete(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.delete(request));
    }
}
