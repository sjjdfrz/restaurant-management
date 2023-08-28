package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.service.OrderService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
    ) {
        ApiResponse<List<OrderDto>> orders = orderService.getAllOrders(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable long id) {

        ApiResponse<OrderDto> orderDto = orderService.getOrderById(id);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createOrder(@Valid @RequestBody OrderDto orderDto) {

        ApiResponse<Object> response = orderService.createOrder(orderDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateOrder(@PathVariable long id, @Valid @RequestBody OrderDto orderDto) {

        ApiResponse<Object> response = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOrder(@PathVariable long id) {

        ApiResponse<Object> response = orderService.deleteOrder(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
