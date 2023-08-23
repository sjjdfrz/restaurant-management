package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.dto.MenuItemDto;
import com.neshan.restaurantmanagement.model.entity.MenuItem;
import com.neshan.restaurantmanagement.repository.MenuItemRepository;
import com.neshan.restaurantmanagement.util.MapStructMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MapStructMapper mapStructMapper;

    public List<MenuItemDto> getAllMenuItems() {

        return menuItemRepository
                .findAll()
                .stream()
                .map(mapStructMapper::menuItemToMenuItemDto)
                .toList();
    }

    public MenuItemDto getMenuItemById(long id) {

        MenuItem menuItem = menuItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu item with ID %d was not found.", id)));

        return mapStructMapper.menuItemToMenuItemDto(menuItem);
    }

    public void createMenuItem(MenuItemDto menuItemDto) {

        MenuItem menuItem = mapStructMapper.menuItemDtoToMenuItem(menuItemDto);
        menuItemRepository.save(menuItem);
    }

    public void updateMenuItem(long id, MenuItemDto menuItemRequest) {

        MenuItem menuItem = menuItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu item with ID %d was not found.", id)));

        menuItem.setName(menuItemRequest.name());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(long id) {
        MenuItem menuItem = menuItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu item with ID %d was not found.", id)));

        menuItemRepository.delete(menuItem);
    }
}
