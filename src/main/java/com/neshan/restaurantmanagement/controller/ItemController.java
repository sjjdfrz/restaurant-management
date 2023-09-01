package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.ItemDto;
import com.neshan.restaurantmanagement.service.ItemService;
import com.neshan.restaurantmanagement.util.AppConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<ApiResponse<List<ItemDto>>> getAllItems(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
    ) {
        List<ItemDto> Items = itemService.getAllItems(pageNo, pageSize, sortBy);

        ApiResponse<List<ItemDto>> response = ApiResponse
                .<List<ItemDto>>builder()
                .status("success")
                .data(Items)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ApiResponse<ItemDto>> getItem(@PathVariable long id) {

        ItemDto itemDto = itemService.getItem(id);

        ApiResponse<ItemDto> response = ApiResponse
                .<ItemDto>builder()
                .status("success")
                .data(itemDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories/{categoryId}/items")
    public ResponseEntity<ApiResponse<List<ItemDto>>> getAllItemsOfCategory(@PathVariable long categoryId) {

        List<ItemDto> Items = itemService.getAllItemsOfCategory(categoryId);

        ApiResponse<List<ItemDto>> response = ApiResponse
                .<List<ItemDto>>builder()
                .status("success")
                .data(Items)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/categories/{categoryId}/items")
    public ResponseEntity<ApiResponse<Object>> createItem(
            @PathVariable long categoryId,
            @Valid @RequestBody ItemDto itemDto) {

        itemService.createItem(categoryId, itemDto);

        ApiResponse<Object> response = ApiResponse
                .builder()
                .status("success")
                .message("Item was created successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Object>> updateItem(@PathVariable long id, @RequestBody ItemDto itemDto) {

        itemService.updateItem(id, itemDto);

        ApiResponse<Object> response = ApiResponse
                .builder()
                .status("success")
                .message("Item was updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteItem(@PathVariable long id) {

        itemService.deleteItem(id);


        ApiResponse<Object> response = ApiResponse
                .builder()
                .status("success")
                .message("Item was deleted successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/items")
    public ResponseEntity<ApiResponse<Object>> deleteAllItems() {
        itemService.deleteAllItems();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/categories/{categoryId}/items")
    public ResponseEntity<ApiResponse<Object>> deleteAllItemsOfCategory(@PathVariable long categoryId) {
        itemService.deleteAllItemsOfCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
