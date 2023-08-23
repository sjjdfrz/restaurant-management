package com.neshan.restuarantmanagement.controller;

import com.neshan.restuarantmanagement.model.ApiResponse;
import com.neshan.restuarantmanagement.model.dto.RestaurantDto;
import com.neshan.restuarantmanagement.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RestaurantDto>>> getAllRestaurants() {

        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();

        ApiResponse<List<RestaurantDto>> apiResponse = ApiResponse
                .<List<RestaurantDto>>builder()
                .status("success")
                .data(restaurants)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantDto>> getRestaurantById(@PathVariable long id) {

        RestaurantDto restaurantDto = restaurantService.getRestaurantById(id);

        ApiResponse<RestaurantDto> apiResponse = ApiResponse
                .<RestaurantDto>builder()
                .status("success")
                .data(restaurantDto)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {

        restaurantService.createRestaurant(restaurantDto);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was created successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateRestaurant(@PathVariable long id, @Valid @RequestBody RestaurantDto restaurantDto) {


        restaurantService.updateRestaurant(id, restaurantDto);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was updated successfully.")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteRestaurant(@PathVariable long id) {

        restaurantService.deleteRestaurant(id);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was deleted successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
