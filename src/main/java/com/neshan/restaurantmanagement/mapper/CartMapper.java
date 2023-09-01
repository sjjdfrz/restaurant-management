package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.CartDto;
import com.neshan.restaurantmanagement.model.entity.Cart;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface CartMapper {

    Cart cartDtoToCart(CartDto cartDto);

    CartDto cartToCartDto(Cart cart);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCartFromDto(CartDto dto, @MappingTarget Cart cart);
}
