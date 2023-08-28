package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.OrderItemMapper;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.entity.OrderItem;
import com.neshan.restaurantmanagement.model.dto.OrderItemDto;
import com.neshan.restaurantmanagement.repository.OrderItemRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {

    private OrderItemRepository orderItemRepository;
    private OrderItemMapper orderItemMapper;

    public ApiResponse<List<OrderItemDto>> getAllOrderItems(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        List<OrderItemDto> pagedResult = orderItemRepository
                .findAll(paging)
                .map(orderItem -> orderItemMapper.orderItemToOrderItemDto(orderItem))
                .getContent();

        return ApiResponse
                .<List<OrderItemDto>>builder()
                .status("success")
                .data(pagedResult)
                .build();
    }

    public ApiResponse<OrderItemDto> getOrderItemById(long id) {

        OrderItem orderItem = orderItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The orderItem with ID %d was not found.", id)));

        OrderItemDto orderItemDto = orderItemMapper.orderItemToOrderItemDto(orderItem);
        return ApiResponse
                .<OrderItemDto>builder()
                .status("success")
                .data(orderItemDto)
                .build();
    }

    public ApiResponse<Object> createOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemMapper.orderItemDtoToOrderItem(orderItemDto);
        orderItemRepository.save(orderItem);

        return ApiResponse
                .builder()
                .status("success")
                .message("OrderItem was created successfully.")
                .build();
    }

    public ApiResponse<Object> updateOrderItem(long id, OrderItemDto orderItemRequest) {

        OrderItem orderItem = orderItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The orderItem with ID %d was not found.", id)));

        orderItemMapper.updateOrderItemFromDto(orderItemRequest, orderItem);
        orderItemRepository.save(orderItem);

        return ApiResponse
                .builder()
                .status("success")
                .message("OrderItem was updated successfully.")
                .build();
    }

    public ApiResponse<Object> deleteOrderItem(long id) {

        OrderItem orderItem = orderItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The orderItem with ID %d was not found.", id)));

        orderItemRepository.delete(orderItem);

        return ApiResponse
                .builder()
                .status("success")
                .message("OrderItem was deleted successfully.")
                .build();
    }
}
