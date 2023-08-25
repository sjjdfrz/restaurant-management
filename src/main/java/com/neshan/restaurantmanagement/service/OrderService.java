package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.OrderMapper;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.entity.Order;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    public ApiResponse<Page<OrderDto>> getAllOrders(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable paging = PageRequest.of(pageNo, pageSize, sort);
        Page<OrderDto> pagedResult = orderRepository
                .findAll(paging)
                .map(order -> orderMapper.orderToOrderDto(order));

        return ApiResponse
                .<Page<OrderDto>>builder()
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
