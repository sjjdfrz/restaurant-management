package com.neshan.restuarantmanagement.util;

import com.neshan.restuarantmanagement.model.dto.RestaurantDto;
import com.neshan.restuarantmanagement.model.entity.Restaurant;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RestuarantMapper {

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);
    Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRestaurantFromDto(RestaurantDto dto, @MappingTarget Restaurant entity);
}

