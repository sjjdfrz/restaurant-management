package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.OrderMapper;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.entity.Order;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.repository.OrderRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    public ApiResponse<List<OrderDto>> getAllOrders(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        List<OrderDto> pagedResult = orderRepository
                .findAll(paging)
                .map(order -> orderMapper.orderToOrderDto(order))
                .getContent();

        return ApiResponse
                .<List<OrderDto>>builder()
                .status("success")
                .data(pagedResult)
                .build();
    }

    public ApiResponse<OrderDto> getOrderById(long id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        OrderDto orderDto = orderMapper.orderToOrderDto(order);
        return ApiResponse
                .<OrderDto>builder()
                .status("success")
                .data(orderDto)
                .build();
    }

    public ApiResponse<Object> createOrder(OrderDto orderDto) {
        Order order = orderMapper.orderDtoToOrder(orderDto);
        orderRepository.save(order);

        return ApiResponse
                .builder()
                .status("success")
                .message("Order was created successfully.")
                .build();
    }

    public ApiResponse<Object> updateOrder(long id, OrderDto orderRequest) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        orderMapper.updateOrderFromDto(orderRequest, order);
        orderRepository.save(order);

        return ApiResponse
                .builder()
                .status("success")
                .message("Order was updated successfully.")
                .build();
    }

    public ApiResponse<Object> deleteOrder(long id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        orderRepository.delete(order);

        return ApiResponse
                .builder()
                .status("success")
                .message("Order was deleted successfully.")
                .build();
    }
}
