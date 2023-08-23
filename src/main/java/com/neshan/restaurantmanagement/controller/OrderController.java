package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders() {

        List<OrderDto> orders = orderService.getAllOrders();

        ApiResponse<List<OrderDto>> apiResponse = ApiResponse
                .<List<OrderDto>>builder()
                .status("success")
                .data(orders)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable long id) {

        OrderDto orderDto = orderService.getOrderById(id);

        ApiResponse<OrderDto> apiResponse = ApiResponse
                .<OrderDto>builder()
                .status("success")
                .data(orderDto)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createOrder(@Valid @RequestBody OrderDto orderDto) {

        orderService.createOrder(orderDto);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("Order was created successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateOrder(@PathVariable long id, @Valid @RequestBody OrderDto orderDto) {


        orderService.updateOrder(id, orderDto);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("Order was updated successfully.")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOrder(@PathVariable long id) {

        orderService.deleteOrder(id);

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("Order was deleted successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
