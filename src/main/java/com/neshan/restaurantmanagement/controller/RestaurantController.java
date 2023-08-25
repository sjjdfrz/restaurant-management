package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.service.RestaurantService;
import com.neshan.restaurantmanagement.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<RestaurantDto>>> getAllRestaurants(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        ApiResponse<Page<RestaurantDto>> restaurants = restaurantService.getAllRestaurants(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantDto>> getRestaurantById(@PathVariable long id) {

        ApiResponse<RestaurantDto> restaurantDto = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurantDto);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {

        ApiResponse<Object> response = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateRestaurant(@PathVariable long id, @Valid @RequestBody RestaurantDto restaurantDto) {

        ApiResponse<Object> response = restaurantService.updateRestaurant(id, restaurantDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteRestaurant(@PathVariable long id) {

        ApiResponse<Object> response = restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
