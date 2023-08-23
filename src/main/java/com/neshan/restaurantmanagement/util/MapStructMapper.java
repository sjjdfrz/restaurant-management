package com.neshan.restaurantmanagement.util;

import com.neshan.restaurantmanagement.model.dto.MenuItemDto;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.model.entity.Menu;
import com.neshan.restaurantmanagement.model.dto.MenuDto;
import com.neshan.restaurantmanagement.model.entity.MenuItem;
import com.neshan.restaurantmanagement.model.entity.User;
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
