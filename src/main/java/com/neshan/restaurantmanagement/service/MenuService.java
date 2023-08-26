package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.MenuMapper;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.entity.Menu;
import com.neshan.restaurantmanagement.model.dto.MenuDto;
import com.neshan.restaurantmanagement.repository.MenuRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private MenuRepository menuRepository;
    private MenuMapper menuMapper;

    public ApiResponse<Page<MenuDto>> getAllMenus(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        Page<MenuDto> pagedResult = menuRepository
                .findAll(paging)
                .map(menu -> menuMapper.menuToMenuDto(menu));

        return ApiResponse
                .<Page<MenuDto>>builder()
                .status("success")
                .data(pagedResult)
                .build();
    }

    public ApiResponse<MenuDto> getMenuById(long id) {

        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        MenuDto menuDto = menuMapper.menuToMenuDto(menu);
        return ApiResponse
                .<MenuDto>builder()
                .status("success")
                .data(menuDto)
                .build();
    }

    public ApiResponse<Object> createMenu(MenuDto menuDto) {
        Menu menu = menuMapper.menuDtoToMenu(menuDto);
        menuRepository.save(menu);

        return ApiResponse
                .builder()
                .status("success")
                .message("Menu was created successfully.")
                .build();
    }

    public ApiResponse<Object> updateMenu(long id, MenuDto menuRequest) {

        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        menuMapper.updateMenuFromDto(menuRequest, menu);
        menuRepository.save(menu);

        return ApiResponse
                .builder()
                .status("success")
                .message("Menu was updated successfully.")
                .build();
    }

    public ApiResponse<Object> deleteMenu(long id) {

        Menu menu = menuRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menu with ID %d was not found.", id)));

        menuRepository.delete(menu);

        return ApiResponse
                .builder()
                .status("success")
                .message("Menu was deleted successfully.")
                .build();
    }
}
