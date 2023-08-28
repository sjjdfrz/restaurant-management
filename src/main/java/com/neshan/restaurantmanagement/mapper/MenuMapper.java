package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.MenuDto;
import com.neshan.restaurantmanagement.model.entity.Menu;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuDto menuToMenuDto(Menu menu);

    Menu menuDtoToMenu(MenuDto menuDto);

    @Mapping(
            target = "id",
            ignore = true,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMenuFromDto(MenuDto dto, @MappingTarget Menu entity);
}
