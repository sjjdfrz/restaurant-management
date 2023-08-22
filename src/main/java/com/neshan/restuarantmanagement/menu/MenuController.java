package com.neshan.restuarantmanagement.menu;

import com.neshan.restuarantmanagement.ApiResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menus")
@AllArgsConstructor
public class MenuController {

    private ModelMapper modelMapper;
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuDto>>> getAllMenus() {

        List<MenuDto> menus = menuService
                .getAllMenus()
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .toList();

        ApiResponse<List<MenuDto>> apiResponse = ApiResponse
                .<List<MenuDto>>builder()
                .status("success")
                .data(menus)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuDto>> getMenuById(@PathVariable int id) {

        Menu menu = menuService.getMenuById(id);
        MenuDto menuResponse = modelMapper.map(menu, MenuDto.class);

        ApiResponse<MenuDto> apiResponse = ApiResponse
                .<MenuDto>builder()
                .status("success")
                .data(menuResponse)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createMenu(@RequestBody MenuDto menuDto) {

        Menu menuRequest = modelMapper.map(menuDto, Menu.class);
        Menu menu = menuService.createMenu(menuRequest);

        ApiResponse<String> apiResponse = ApiResponse
                .<String>builder()
                .status("success")
                .message("Menu was created successfully.")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateMenu(@PathVariable int id, @RequestBody MenuDto menuDto) {

        Menu menuRequest = modelMapper.map(menuDto, Menu.class);
        Menu menu = menuService.updateMenu(id, menuRequest);

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
