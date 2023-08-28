package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto);

    @Mapping(
            target = "id",
            ignore = true,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRestaurantFromDto(RestaurantDto dto, @MappingTarget Restaurant entity);
}

