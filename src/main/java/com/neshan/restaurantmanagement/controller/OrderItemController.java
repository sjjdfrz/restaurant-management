package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.OrderItemDto;
import com.neshan.restaurantmanagement.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItem-items")
@AllArgsConstructor
public class OrderItemController {

    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderItemDto>>> getAllOrderItems() {

        List<OrderItemDto> orderItems = orderItemService.getAllOrderItems();

        ApiResponse<List<OrderItemDto>> apiResponse = ApiResponse
                .<List<OrderItemDto>>builder()
                .status("success")
                .data(orderItems)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItemDto>> getOrderItemById(@PathVariable long id) {

        OrderItemDto orderItemDto = orderItemService.getOrderItemById(id);

        ApiResponse<OrderItemDto> apiResponse = ApiResponse
                .<OrderItemDto>builder()
                .status("success")
                .data(orderItemDto)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {

        orderItemService.createOrderItem(orderItemDto);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("OrderItem was created successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateOrderItem(@PathVariable long id, @Valid @RequestBody OrderItemDto orderItemDto) {


        orderItemService.updateOrderItem(id, orderItemDto);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("OrderItem was updated successfully.")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOrderItem(@PathVariable long id) {

        orderItemService.deleteOrderItem(id);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("OrderItem was deleted successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
