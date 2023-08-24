package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.model.entity.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    OrderDto orderToOrderDto(Order order);
    Order orderDtoToOrder(OrderDto orderDto);

    @Mapping(target = "id", ignore = true)
    void updateOrderFromDto(OrderDto dto, @MappingTarget Order entity);
}
