package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.CategoriesDto;
import com.neshan.restaurantmanagement.model.dto.CategoryDto;
import com.neshan.restaurantmanagement.service.CategoryService;
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
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoriesDto>>> getAllCategories(
            @RequestParam(
                    value = "page",
                    defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(
                    value = "size",
                    defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(
                    value = "sort",
                    defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy
    ) {
        var categories = categoryService.getAllCategories(pageNo, pageSize, sortBy);

        var response = ApiResponse
                .<List<CategoriesDto>>builder()
                .status("success")
                .data(categories)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategory(@PathVariable long id) {

        var category = categoryService.getCategory(id);

        var response = ApiResponse
                .<CategoryDto>builder()
                .status("success")
                .data(category)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/restaurants/{restaurantId}/categories")
    public ResponseEntity<ApiResponse<Object>> createCategory(
            @PathVariable long restaurantId,
            @Valid @RequestBody CategoriesDto categoriesDto) {

        categoryService.createCategory(restaurantId, categoriesDto);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Category was created successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Object>> updateCategory(
            @PathVariable long id,
            @RequestBody CategoriesDto categoriesDto) {

        categoryService.updateCategory(id, categoriesDto);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Category was updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteCategory(@PathVariable long id) {

        categoryService.deleteCategory(id);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Category was deleted successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<ApiResponse<Object>> deleteAllCategories() {
        categoryService.deleteAllCategories();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/restaurants/{restaurantId}/categories")
    public ResponseEntity<ApiResponse<Object>> deleteAllCategoriesOfRestaurant(
            @PathVariable long restaurantId) {
        categoryService.deleteAllCategoriesOfRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
