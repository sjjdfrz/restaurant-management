package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface RestaurantMapper {

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    @Mapping(target = "categories", ignore = true)
    Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRestaurantFromDto(RestaurantDto dto, @MappingTarget Restaurant restaurant);
}

