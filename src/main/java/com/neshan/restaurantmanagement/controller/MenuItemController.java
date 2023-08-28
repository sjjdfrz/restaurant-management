package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.MenuItemDto;
import com.neshan.restaurantmanagement.service.MenuItemService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class MenuItemController {

    private MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuItemDto>>> getAllMenuItems(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
    ) {
        ApiResponse<List<MenuItemDto>> menuItems = menuItemService.getAllMenuItems(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuItemDto>> getMenuItemById(@PathVariable long id) {

        ApiResponse<MenuItemDto> menuItemDto = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(menuItemDto);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createMenuItem(@Valid @RequestBody MenuItemDto menuItemDto) {

        ApiResponse<Object> response = menuItemService.createMenuItem(menuItemDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateMenuItem(@PathVariable long id, @Valid @RequestBody MenuItemDto menuItemDto) {

        ApiResponse<Object> response = menuItemService.updateMenuItem(id, menuItemDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteMenuItem(@PathVariable long id) {

        ApiResponse<Object> response = menuItemService.deleteMenuItem(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
