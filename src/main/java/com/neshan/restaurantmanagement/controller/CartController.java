package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.CartDto;
import com.neshan.restaurantmanagement.model.dto.CartRequestDto;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.service.CartService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity<ApiResponse<List<CartDto>>> getAllCarts(
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
        var carts = cartService.getAllCarts(pageNo, pageSize, sortBy);

        var response = ApiResponse
                .<List<CartDto>>builder()
                .status("success")
                .data(carts)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<ApiResponse<CartDto>> getCart(@PathVariable long id) {

        var cart = cartService.getCart(id);

        var response = ApiResponse
                .<CartDto>builder()
                .status("success")
                .data(cart)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-carts")
    public ResponseEntity<ApiResponse<List<CartDto>>> getAllCartsOfUser(
            @AuthenticationPrincipal User user) {

        var carts = cartService.getAllCartsOfUser(user);

        var response = ApiResponse
                .<List<CartDto>>builder()
                .status("success")
                .data(carts)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("my-carts/{id}")
    public ResponseEntity<ApiResponse<CartDto>> getCartOfUser(
            @AuthenticationPrincipal User user,
            @PathVariable long id
    ) {

        var cart = cartService.getCartOfUser(user, id);

        var response = ApiResponse
                .<CartDto>builder()
                .status("success")
                .data(cart)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/my-carts")
    public ResponseEntity<ApiResponse<Object>> createCart(
            @RequestBody CartRequestDto cartRequestDto,
            @AuthenticationPrincipal User user) {

        cartService.createCart(cartRequestDto, user);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Cart was created successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/my-carts/{id}")
    public ResponseEntity<ApiResponse<Object>> updateCart(
            @PathVariable long id,
            @RequestBody CartRequestDto cartRequestDto) {

        cartService.updateCart(id, cartRequestDto);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Cart was updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/my-carts/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteCart(@PathVariable long id) {

        cartService.deleteCart(id);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Cart was deleted successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/my-carts")
    public ResponseEntity<ApiResponse<Object>> deleteAllCarts(@AuthenticationPrincipal User user) {

        cartService.deleteAllCarts(user);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("All Carts were deleted successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
