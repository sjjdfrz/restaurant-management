package com.neshan.restuarantmanagement.controller;

import com.neshan.restuarantmanagement.model.ApiResponse;

import com.neshan.restuarantmanagement.model.dto.MenuItemDto;
import com.neshan.restuarantmanagement.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
@AllArgsConstructor
public class MenuItemController {

    private MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuItemDto>>> getAllMenuItems() {

        List<MenuItemDto> menuItems = menuItemService.getAllMenuItems();

        ApiResponse<List<MenuItemDto>> apiResponse = ApiResponse
                .<List<MenuItemDto>>builder()
                .status("success")
                .data(menuItems)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuItemDto>> getMenuItemById(@PathVariable long id) {

        MenuItemDto menuItemDto = menuItemService.getMenuItemById(id);

        ApiResponse<MenuItemDto> apiResponse = ApiResponse
                .<MenuItemDto>builder()
                .status("success")
                .data(menuItemDto)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createMenuItem(@Valid @RequestBody MenuItemDto menuItemDto) {

        menuItemService.createMenuItem(menuItemDto);

        ApiResponse<String> apiResponse = ApiResponse
                .<String>builder()
                .status("success")
                .message("Menu item was created successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateMenuItem(@PathVariable long id, @Valid @RequestBody MenuItemDto menuItemDto) {


        menuItemService.updateMenuItem(id, menuItemDto);

        ApiResponse<String> apiResponse = ApiResponse
                .<String>builder()
                .status("success")
                .message("Menu item was updated successfully.")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMenuItem(@PathVariable long id) {

        menuItemService.deleteMenuItem(id);

        ApiResponse<String> apiResponse = ApiResponse
                .<String>builder()
                .status("success")
                .message("Menu item was deleted successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
