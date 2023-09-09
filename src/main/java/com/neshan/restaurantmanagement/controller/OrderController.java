package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.ItemStatsDto;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.model.dto.OrdersDto;
import com.neshan.restaurantmanagement.model.dto.SalesStatsDto;
import com.neshan.restaurantmanagement.model.entity.Order;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.service.OrderService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrdersDto>>> getAllOrders(
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
        var orders = orderService.getAllOrders(pageNo, pageSize, sortBy);

        var response = ApiResponse
                .<List<OrdersDto>>builder()
                .status("success")
                .data(orders)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrder(@PathVariable long id) {

        var order = orderService.getOrder(id);

        var response = ApiResponse
                .<OrderDto>builder()
                .status("success")
                .data(order)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<OrdersDto>>> getAllOrdersOfUser(
            @AuthenticationPrincipal User user) {

        var orders = orderService.getAllOrdersOfUser(user);

        var response = ApiResponse
                .<List<OrdersDto>>builder()
                .status("success")
                .data(orders)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-orders/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderOfUser(
            @PathVariable long id,
            @AuthenticationPrincipal User user) {

        var order = orderService.getOrderOfUser(user, id);

        var response = ApiResponse
                .<OrderDto>builder()
                .status("success")
                .data(order)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/carts/{cartId}/orders")
    public ResponseEntity<ApiResponse<OrdersDto>> createOrder(
            @PathVariable long cartId,
            @AuthenticationPrincipal User user) {

        var order = orderService.createOrder(cartId, user);

        var response = ApiResponse
                .<OrdersDto>builder()
                .status("success")
                .message("Order was created successfully.")
                .data(order)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Object>> updateOrder(
            @PathVariable long id,
            @RequestBody OrdersDto ordersDto) {

        orderService.updateOrder(id, ordersDto);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Order was updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOrder(@PathVariable long id) {

        orderService.deleteOrder(id);

        var response = ApiResponse
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

        var response = ApiResponse
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

        var salesStats = orderService.getSalesStatsOfLastDays(days);

        var response = ApiResponse
                .<SalesStatsDto>builder()
                .status("success")
                .data(salesStats)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-items/")
    public ResponseEntity<ApiResponse<List<ItemStatsDto>>> getTopItems(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {

        var topItems = orderService.getTopItems(from, to);

        var response = ApiResponse
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

        var topItems = orderService.getTopItemsOfLastDays(days);

        var response = ApiResponse
                .<List<ItemStatsDto>>builder()
                .status("success")
                .data(topItems)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/order-history/")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersBetween(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {

        var topItems = orderService.getOrdersBetween(from, to);

        var response = ApiResponse
                .<List<Order>>builder()
                .status("success")
                .data(topItems)
                .build();

        return ResponseEntity.ok(response);
    }
}
