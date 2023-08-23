package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.util.MapStructMapper;
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
    private MapStructMapper mapStructMapper;

    public List<MenuDto> getAllMenus() {

        return menuRepository
                .findAll()
                .stream()
                .map(mapStructMapper::menuToMenuDto)
                .toList();
    }

    public MenuDto getMenuById(long id) {

        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        return mapStructMapper.menuToMenuDto(menu);
    }

    public void createMenu(MenuDto menuDto) {
        Menu menu = mapStructMapper.menuDtoToMenu(menuDto);
        menuRepository.save(menu);
    }

    public void updateMenu(long id, MenuDto menuRequest) {
        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        menu.setTitle(menuRequest.title());
        menu.setDescription(menuRequest.description());
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
