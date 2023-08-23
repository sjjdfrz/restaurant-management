package com.neshan.restaurantmanagement.controller;


import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {

        List<UserDto> users = userService.getAllUsers();

        ApiResponse<List<UserDto>> apiResponse = ApiResponse
                .<List<UserDto>>builder()
                .status("success")
                .data(users)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable long id) {

        UserDto userDto = userService.getUserById(id);

        ApiResponse<UserDto> apiResponse = ApiResponse
                .<UserDto>builder()
                .status("success")
                .data(userDto)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable long id) {

        userService.deleteUser(id);

        ApiResponse<String> apiResponse = ApiResponse
                .<String>builder()
                .status("success")
                .message("User was deleted successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
