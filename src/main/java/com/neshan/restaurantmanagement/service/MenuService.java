package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.MenuMapper;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.entity.Menu;
import com.neshan.restaurantmanagement.model.dto.MenuDto;
import com.neshan.restaurantmanagement.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private MenuRepository menuRepository;
    private MenuMapper menuMapper;

    public List<MenuDto> getAllMenus() {

        return menuRepository
                .findAll()
                .stream()
                .map(menuMapper::menuToMenuDto)
                .toList();
    }

    public MenuDto getMenuById(long id) {

        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        return menuMapper.menuToMenuDto(menu);
    }

    public void createMenu(MenuDto menuDto) {
        Menu menu = menuMapper.menuDtoToMenu(menuDto);
        menuRepository.save(menu);
    }

    public void updateMenu(long id, MenuDto menuRequest) {
        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        menuMapper.updateMenuFromDto(menuRequest, menu);
        menuRepository.save(menu);
    }

    public void deleteMenu(long id) {

        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        menuRepository.delete(menu);
    }
}
