package com.neshan.restuarantmanagement.util;

import com.neshan.restuarantmanagement.model.dto.MenuItemDto;
import com.neshan.restuarantmanagement.model.dto.UserDto;
import com.neshan.restuarantmanagement.model.entity.Menu;
import com.neshan.restuarantmanagement.model.dto.MenuDto;
import com.neshan.restuarantmanagement.model.entity.MenuItem;
import com.neshan.restuarantmanagement.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    MenuDto menuToMenuDto(Menu menu);
    Menu menuDtoToMenu(MenuDto menuDto);

    MenuItemDto menuItemToMenuItemDto(MenuItem menuItem);
    MenuItem menuItemDtoToMenuItem(MenuItemDto menuItemDto);

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);

}
