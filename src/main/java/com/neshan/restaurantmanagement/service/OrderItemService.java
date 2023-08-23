package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.dto.OrderItemDto;
import com.neshan.restaurantmanagement.model.entity.OrderItem;
import com.neshan.restaurantmanagement.repository.OrderItemRepository;
import com.neshan.restaurantmanagement.util.OrderItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {

    private OrderItemRepository orderItemRepository;
    private OrderItemMapper orderItemMapper;

    public List<OrderItemDto> getAllOrderItems() {

        return orderItemRepository
                .findAll()
                .stream()
                .map(orderItemMapper::orderItemToOrderItemDto)
                .toList();
    }

    public OrderItemDto getOrderItemById(long id) {

        OrderItem orderItem = orderItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The orderItem with ID %d was not found.", id)));

        return orderItemMapper.orderItemToOrderItemDto(orderItem);
    }

    public void createOrderItem(OrderItemDto orderItemDto) {

        OrderItem orderItem = orderItemMapper.orderItemDtoToOrderItem(orderItemDto);
        orderItemRepository.save(orderItem);
    }

    public void updateOrderItem(long id, OrderItemDto orderItemRequest) {

        OrderItem orderItem = orderItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The orderItem with ID %d was not found.", id)));

        orderItemMapper.updateOrderItemFromDto(orderItemRequest, orderItem);
        orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(long id) {

        OrderItem orderItem = orderItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The orderItem with ID %d was not found.", id)));

        orderItemRepository.delete(orderItem);
    }
}
