package com.neshan.restuarantmanagement.service;

import com.neshan.restuarantmanagement.util.MapStructMapper;
import com.neshan.restuarantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restuarantmanagement.model.entity.Menu;
import com.neshan.restuarantmanagement.model.dto.MenuDto;
import com.neshan.restuarantmanagement.repository.MenuRepository;
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
                .map(menu -> mapStructMapper.menuToMenuDto(menu))
                .toList();
    }

    public MenuDto getMenuById(int id) {

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

    public void updateMenu(int id, MenuDto menuRequest) {
        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        menu.setTitle(menuRequest.title());
        menu.setDescription(menuRequest.description());
        menuRepository.save(menu);
    }

    public void deleteMenu(int id) {

        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        menuRepository.delete(menu);
    }
}
