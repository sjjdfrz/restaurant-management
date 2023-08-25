package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.service.UserService;
import com.neshan.restaurantmanagement.util.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        ApiResponse<Page<UserDto>> users = userService.getAllUsers(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable long id) {

        ApiResponse<UserDto> userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable long id) {

        ApiResponse<Object> response = userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
