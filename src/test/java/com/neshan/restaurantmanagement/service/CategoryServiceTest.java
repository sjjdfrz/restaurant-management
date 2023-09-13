package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.CategoryMapper;
import com.neshan.restaurantmanagement.model.dto.CategoriesDto;
import com.neshan.restaurantmanagement.model.entity.Category;
import com.neshan.restaurantmanagement.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Test
    void testGetAllCategoriesWhenCacheIsEmpty() {

        // Given
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "title";

        Category category1 = Category.builder().title("pizza").build();
        Category category2 = Category.builder().title("burger").build();
        CategoriesDto categoriesDto1 = CategoriesDto.builder().title("pizza").build();
        CategoriesDto categoriesDto2 = CategoriesDto.builder().title("burger").build();

        categoryRepository.saveAll(List.of(category1, category2));

        List<Category> categories = new ArrayList<>(List.of(category1, category2));
        List<CategoriesDto> categoriesDtos = new ArrayList<>(List.of(categoriesDto1, categoriesDto2));
        Page<Category> page = new PageImpl<>(categories);

        // When
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryMapper.categoryToCategoriesDto(any(Category.class)))
                .thenAnswer(invocation -> {
                    Category category = invocation.getArgument(0);
                    return new CategoriesDto(category.getId(), category.getTitle());
                });
        List<CategoriesDto> result = categoryService.getAllCategories(pageNo, pageSize, sortBy);

        // Then
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
        verify(categoryMapper, times(categories.size())).categoryToCategoriesDto(any(Category.class));

        assertEquals(categoriesDtos.size(), result.size());
        assertEquals(categoriesDtos.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(categoriesDtos.get(1).getTitle(), result.get(1).getTitle());
    }
}