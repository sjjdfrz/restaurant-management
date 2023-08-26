package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.OrderItemDto;
import com.neshan.restaurantmanagement.service.OrderItemService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-items")
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class OrderItemController {

    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderItemDto>>> getAllOrderItems(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
    ) {
        ApiResponse<Page<OrderItemDto>> orderItems = orderItemService.getAllOrderItems(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItemDto>> getOrderItemById(@PathVariable long id) {

        ApiResponse<OrderItemDto> orderItemDto = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItemDto);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {

        ApiResponse<Object> response = orderItemService.createOrderItem(orderItemDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateOrderItem(@PathVariable long id, @Valid @RequestBody OrderItemDto orderItemDto) {

        ApiResponse<Object> response = orderItemService.updateOrderItem(id, orderItemDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOrderItem(@PathVariable long id) {

        ApiResponse<Object> response = orderItemService.deleteOrderItem(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
