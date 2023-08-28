package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.MenuItemMapper;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.entity.MenuItem;
import com.neshan.restaurantmanagement.model.dto.MenuItemDto;
import com.neshan.restaurantmanagement.repository.MenuItemRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuItemService {

    private MenuItemRepository menuItemRepository;
    private MenuItemMapper menuItemMapper;

    public ApiResponse<List<MenuItemDto>> getAllMenuItems(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        List<MenuItemDto> pagedResult = menuItemRepository
                .findAll(paging)
                .map(menuItem -> menuItemMapper.menuItemToMenuItemDto(menuItem))
                .getContent();

        return ApiResponse
                .<List<MenuItemDto>>builder()
                .status("success")
                .data(pagedResult)
                .build();
    }

    public ApiResponse<MenuItemDto> getMenuItemById(long id) {

        MenuItem menuItem = menuItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menuItem with ID %d was not found.", id)));

        MenuItemDto menuItemDto = menuItemMapper.menuItemToMenuItemDto(menuItem);
        return ApiResponse
                .<MenuItemDto>builder()
                .status("success")
                .data(menuItemDto)
                .build();
    }

    public ApiResponse<Object> createMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemMapper.menuItemDtoToMenuItem(menuItemDto);
        menuItemRepository.save(menuItem);

        return ApiResponse
                .builder()
                .status("success")
                .message("MenuItem was created successfully.")
                .build();
    }

    public ApiResponse<Object> updateMenuItem(long id, MenuItemDto menuItemRequest) {

        MenuItem menuItem = menuItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menuItem with ID %d was not found.", id)));

        menuItemMapper.updateMenuItemFromDto(menuItemRequest, menuItem);
        menuItemRepository.save(menuItem);

        return ApiResponse
                .builder()
                .status("success")
                .message("MenuItem was updated successfully.")
                .build();
    }

    public ApiResponse<Object> deleteMenuItem(long id) {

        MenuItem menuItem = menuItemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The menuItem with ID %d was not found.", id)));

        menuItemRepository.delete(menuItem);

        return ApiResponse
                .builder()
                .status("success")
                .message("MenuItem was deleted successfully.")
                .build();
    }
}
