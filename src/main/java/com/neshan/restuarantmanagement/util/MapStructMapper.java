package com.neshan.restuarantmanagement.util;

import com.neshan.restuarantmanagement.model.entity.Menu;
import com.neshan.restuarantmanagement.model.dto.MenuDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    MenuDto menuToMenuDto(Menu menu);
    Menu menuDtoToMenu(MenuDto menuDto);
}
