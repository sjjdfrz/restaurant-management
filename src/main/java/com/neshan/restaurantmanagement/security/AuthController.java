package com.neshan.restaurantmanagement.security;


import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterRequest request,
                                                        HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(request, response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PatchMapping("/updateMe")
    public ResponseEntity<ApiResponse<Object>> updateMe(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.update(request));
    }

    @DeleteMapping("/deleteMe")
    public ResponseEntity<ApiResponse<Object>> deleteMe() {
        return ResponseEntity.ok(authenticationService.delete());
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getMe() {
        return ResponseEntity.ok(authenticationService.getMe());
    }
}
