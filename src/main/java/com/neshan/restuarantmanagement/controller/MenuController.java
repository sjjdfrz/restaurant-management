package com.neshan.restuarantmanagement.controller;

import com.neshan.restuarantmanagement.model.ApiResponse;
import com.neshan.restuarantmanagement.model.MenuDto;
import com.neshan.restuarantmanagement.service.MenuService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
@AllArgsConstructor
public class MenuController {

    private MenuService menuService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuDto>>> getAllMenus() {

        List<MenuDto> menus = menuService.getAllMenus();

        ApiResponse<List<MenuDto>> apiResponse = ApiResponse
                .<List<MenuDto>>builder()
                .status("success")
                .data(menus)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuDto>> getMenuById(@PathVariable int id) {

        MenuDto menuDto = menuService.getMenuById(id);

        ApiResponse<MenuDto> apiResponse = ApiResponse
                .<MenuDto>builder()
                .status("success")
                .data(menuDto)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createMenu(@Valid @RequestBody MenuDto menuDto) {

        menuService.createMenu(menuDto);

        ApiResponse<String> apiResponse = ApiResponse
                .<String>builder()
                .status("success")
                .message("Menu was created successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateMenu(@PathVariable int id, @Valid @RequestBody MenuDto menuDto) {


        menuService.updateMenu(id, menuDto);

        ApiResponse<String> apiResponse = ApiResponse
                .<String>builder()
                .status("success")
                .message("Menu was updated successfully.")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable int id) {

        menuService.deleteMenu(id);

        ApiResponse<String> apiResponse = ApiResponse
                .<String>builder()
                .status("success")
                .message("Menu was deleted successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
