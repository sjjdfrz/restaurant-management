package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.ItemDto;
import com.neshan.restaurantmanagement.model.entity.Item;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto itemToItemDto(Item item);

    @Mapping(target = "itemStatus", defaultValue = "AVAILABLE")
    Item itemDtoToItem(ItemDto itemDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateItemFromDto(ItemDto dto, @MappingTarget Item item);
}
