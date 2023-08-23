package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.model.entity.Order;
import com.neshan.restaurantmanagement.repository.OrderRepository;
import com.neshan.restaurantmanagement.util.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {

        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .toList();
    }

    public OrderDto getOrderById(long id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        return orderMapper.orderToOrderDto(order);
    }

    public void createOrder(OrderDto orderDto) {

        Order order = orderMapper.orderDtoToOrder(orderDto);
        orderRepository.save(order);
    }

    public void updateOrder(long id, OrderDto orderRequest) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        orderMapper.updateOrderFromDto(orderRequest, order);
        orderRepository.save(order);
    }

    public void deleteOrder(long id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        orderRepository.delete(order);
    }
}
