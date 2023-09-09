package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.model.dto.OrdersDto;
import com.neshan.restaurantmanagement.model.entity.Order;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface OrderMapper {

    OrdersDto orderToOrdersDto(Order order);

    OrderDto orderToOrderDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalCost", ignore = true)
    @Mapping(target = "items", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(OrdersDto dto, @MappingTarget Order order);
}
