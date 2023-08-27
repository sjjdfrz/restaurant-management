package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.service.RestaurantService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class RestaurantController {

    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RestaurantDto>>> getAllRestaurants(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy

    ) {
        ApiResponse<List<RestaurantDto>> restaurants = restaurantService.getAllRestaurants(pageNo, pageSize, sortBy);
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

//    private void fallbackMethod(RequestNotPermitted requestNotPermitted) {
//        throw requestNotPermitted;
//    }
}
