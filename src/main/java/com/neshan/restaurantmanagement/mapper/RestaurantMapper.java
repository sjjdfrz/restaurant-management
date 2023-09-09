package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.model.dto.RestaurantsDto;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface RestaurantMapper {

    RestaurantsDto restaurantToRestaurantsDto(Restaurant restaurant);

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    @Mapping(target = "categories", ignore = true)
    Restaurant restaurantsDtoToRestaurant(RestaurantsDto restaurantsDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRestaurantFromDto(RestaurantsDto dto, @MappingTarget Restaurant restaurant);
}

