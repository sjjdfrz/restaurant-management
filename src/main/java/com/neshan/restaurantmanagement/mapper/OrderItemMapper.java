package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.OrderItemDto;
import com.neshan.restaurantmanagement.model.entity.OrderItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderItemMapper {

    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);
    OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto);

    @Mapping(target = "id", ignore = true)
    void updateOrderItemFromDto(OrderItemDto dto, @MappingTarget OrderItem entity);
}
