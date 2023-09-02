package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.security.RegisterRequest;
import com.neshan.restaurantmanagement.service.UserService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers(
            @RequestParam(
                    value = "page",
                    defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(
                    value = "size",
                    defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(
                    value = "sort",
                    defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy
    ) {
        var users = userService.getAllUsers(pageNo, pageSize, sortBy);

        var response = ApiResponse
                .<List<UserDto>>builder()
                .status("success")
                .data(users)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable long id) {

        var user = userService.getUser(id);

        var response = ApiResponse
                .<UserDto>builder()
                .status("success")
                .data(user)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable long id) {

        userService.deleteUser(id);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("User was deleted successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update-me")
    @RateLimiter(name = "rate-limit")
    public ResponseEntity<ApiResponse<Object>> updateMe(
            @RequestBody RegisterRequest registerRequest,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(userService.updateMe(registerRequest, httpRequest));
    }

    @DeleteMapping("/delete-me")
    @RateLimiter(name = "rate-limit")
    public ResponseEntity<ApiResponse<Object>> deleteMe(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(userService.deleteMe(httpRequest));
    }

    @GetMapping("/me")
    @RateLimiter(name = "rate-limit")
    public ResponseEntity<ApiResponse<UserDto>> getMe(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(userService.getMe(httpRequest));
    }
}
