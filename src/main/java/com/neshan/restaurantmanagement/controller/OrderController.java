package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.ItemStatsDto;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.model.dto.SalesStatsDto;
import com.neshan.restaurantmanagement.model.entity.Order;
import com.neshan.restaurantmanagement.service.OrderService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders(
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
        List<OrderDto> orders = orderService.getAllOrders(pageNo, pageSize, sortBy);

        ApiResponse<List<OrderDto>> response = ApiResponse
                .<List<OrderDto>>builder()
                .status("success")
                .data(orders)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrder(@PathVariable long id) {

        OrderDto orderDto = orderService.getOrder(id);

        ApiResponse<OrderDto> response = ApiResponse
                .<OrderDto>builder()
                .status("success")
                .data(orderDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrdersOfUser(
            HttpServletRequest request) {

        List<OrderDto> orders = orderService.getAllOrdersOfUser(request);

        ApiResponse<List<OrderDto>> response = ApiResponse
                .<List<OrderDto>>builder()
                .status("success")
                .data(orders)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-orders/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderOfUser(
            HttpServletRequest request,
            @PathVariable long id) {

        OrderDto orderDto = orderService.getOrderOfUser(request, id);

        ApiResponse<OrderDto> response = ApiResponse
                .<OrderDto>builder()
                .status("success")
                .data(orderDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/carts/{cartId}/orders")
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(
            @PathVariable long cartId,
            HttpServletRequest request) {

        OrderDto order = orderService.createOrder(cartId, request);

        ApiResponse<OrderDto> response = ApiResponse
                .<OrderDto>builder()
                .status("success")
                .message("Order was created successfully.")
                .data(order)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Object>> updateOrder(
            @PathVariable long id,
            @RequestBody OrderDto orderDto) {

        orderService.updateOrder(id, orderDto);

        ApiResponse<Object> response = ApiResponse
                .builder()
                .status("success")
                .message("Order was updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOrder(@PathVariable long id) {

        orderService.deleteOrder(id);

        ApiResponse<Object> response = ApiResponse
                .builder()
                .status("success")
                .message("Order was deleted successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/orders")
    public ResponseEntity<ApiResponse<Object>> deleteAllOrders() {
        orderService.deleteAllOrders();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/sales-stats/")
    public ResponseEntity<ApiResponse<SalesStatsDto>> getSalesStats(

            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {

        SalesStatsDto salesStats = orderService.getSalesStats(from, to);

        ApiResponse<SalesStatsDto> response = ApiResponse
                .<SalesStatsDto>builder()
                .status("success")
                .data(salesStats)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sales-stats/{days}")
    public ResponseEntity<ApiResponse<SalesStatsDto>> getSalesStatsOfLastDays(
            @PathVariable int days
    ) {

        SalesStatsDto salesStats = orderService.getSalesStatsOfLastDays(days);

        ApiResponse<SalesStatsDto> response = ApiResponse
                .<SalesStatsDto>builder()
                .status("success")
                .data(salesStats)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-items")
    public ResponseEntity<ApiResponse<List<ItemStatsDto>>> getTopItems(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {

        List<ItemStatsDto> topItems = orderService.getTopItems(from, to);

        ApiResponse<List<ItemStatsDto>> response = ApiResponse
                .<List<ItemStatsDto>>builder()
                .status("success")
                .data(topItems)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-items/{days}")
    public ResponseEntity<ApiResponse<List<ItemStatsDto>>> getTopItemsOfLastDays(
            @PathVariable int days
    ) {

        List<ItemStatsDto> topItems = orderService.getTopItemsOfLastDays(days);

        ApiResponse<List<ItemStatsDto>> response = ApiResponse
                .<List<ItemStatsDto>>builder()
                .status("success")
                .data(topItems)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersBetween(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {

        List<Order> topItems = orderService.getOrdersBetween(from, to);

        ApiResponse<List<Order>> response = ApiResponse
                .<List<Order>>builder()
                .status("success")
                .data(topItems)
                .build();

        return ResponseEntity.ok(response);
    }
}
