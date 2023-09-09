package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.model.dto.RestaurantsDto;
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
    public ResponseEntity<ApiResponse<List<RestaurantsDto>>> getAllRestaurants(
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

        var restaurants = restaurantService.getAllRestaurants(pageNo, pageSize, sortBy);

        var response = ApiResponse
                .<List<RestaurantsDto>>builder()
                .status("success")
                .data(restaurants)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantDto>> getRestaurant(
            @PathVariable long id) {

        var restaurant = restaurantService.getRestaurant(id);

        var response = ApiResponse
                .<RestaurantDto>builder()
                .status("success")
                .data(restaurant)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createRestaurant(
            @Valid @RequestBody RestaurantsDto restaurantsDto) {

        restaurantService.createRestaurant(restaurantsDto);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was created successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateRestaurant
            (@PathVariable long id,
             @RequestBody RestaurantsDto restaurantsDto) {

        restaurantService.updateRestaurant(id, restaurantsDto);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteRestaurant(
            @PathVariable long id) {

        restaurantService.deleteRestaurant(id);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was deleted successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Object>> deleteAllRestaurants() {
        restaurantService.deleteAllRestaurants();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
