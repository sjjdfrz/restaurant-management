package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.MenuDto;
import com.neshan.restaurantmanagement.service.MenuService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class MenuController {

    private MenuService menuService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuDto>>> getAllMenus(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
    ) {
        ApiResponse<List<MenuDto>> menus = menuService.getAllMenus(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuDto>> getMenuById(@PathVariable long id) {

        ApiResponse<MenuDto> menuDto = menuService.getMenuById(id);
        return ResponseEntity.ok(menuDto);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createMenu(@Valid @RequestBody MenuDto menuDto) {

        ApiResponse<Object> response = menuService.createMenu(menuDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateMenu(@PathVariable long id, @Valid @RequestBody MenuDto menuDto) {

        ApiResponse<Object> response = menuService.updateMenu(id, menuDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteMenu(@PathVariable long id) {

        ApiResponse<Object> response = menuService.deleteMenu(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
