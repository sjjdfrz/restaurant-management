package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.MenuItemDto;
import com.neshan.restaurantmanagement.model.entity.MenuItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MenuItemMapper {

    MenuItemDto menuItemToMenuItemDto(MenuItem menuItem);
    MenuItem menuItemDtoToMenuItem(MenuItemDto menuItemDto);

    @Mapping(target = "id", ignore = true)
    void updateMenuItemFromDto(MenuItemDto dto, @MappingTarget MenuItem entity);
}
