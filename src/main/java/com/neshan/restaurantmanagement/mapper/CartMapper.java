package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.CartDto;
import com.neshan.restaurantmanagement.model.entity.Cart;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface CartMapper {

    CartDto cartToCartDto(Cart cart);
}
