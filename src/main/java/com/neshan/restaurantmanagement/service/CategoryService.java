package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.cache.CategoryCache;
import com.neshan.restaurantmanagement.cache.RestaurantCache;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.CategoryMapper;
import com.neshan.restaurantmanagement.model.dto.CategoriesDto;
import com.neshan.restaurantmanagement.model.dto.CategoryDto;
import com.neshan.restaurantmanagement.model.entity.Category;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import com.neshan.restaurantmanagement.repository.CategoryRepository;
import com.neshan.restaurantmanagement.repository.RestaurantRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    private CategoryCache categoryCache;
    private RestaurantCache restaurantCache;
    private RestaurantRepository restaurantRepository;

    @Transactional
    public List<CategoriesDto> getAllCategories(int pageNo, int pageSize, String sortBy) {

        if (categoryCache.getCategories().isEmpty()) {

            List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
            Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

            return categoryRepository
                    .findAll(paging)
                    .map(category -> categoryMapper.categoryToCategoriesDto(category))
                    .getContent();
        }

        return categoryCache
                .getCategories()
                .values()
                .stream()
                .map(category -> categoryMapper.categoryToCategoriesDto(category))
                .toList();
    }

    @Transactional
    public CategoryDto getCategory(long id) {

        if (categoryCache.getCategories().isEmpty()) {

            Category category = categoryRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementFoundException(
                            String.format("The category with ID %d was not found.", id)));

            return categoryMapper.categoryToCategoryDto(category);
        }

        return categoryMapper
                .categoryToCategoryDto(categoryCache.getCategories().get(id));
    }

    @Transactional
    public void createCategory(long restaurantId, CategoriesDto categoriesDto) {

        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", restaurantId)));

        Category category = categoryMapper.categoriesDtoToCategory(categoriesDto);
        restaurant.addCategory(category);
        restaurantRepository.save(restaurant);

        // TODO update categoryCache
    }

    @Transactional
    public void updateCategory(long id, CategoriesDto categoryRequest) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The category with ID %d was not found.", id)));

        categoryMapper.updateCategoryFromDto(categoryRequest, category);
        categoryRepository.save(category);

        categoryCache.update(id, category);
    }

    @Transactional
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
        categoryCache.delete(id);
    }

    @Transactional
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
        categoryCache.deleteAll();
    }

    @Transactional
    public void deleteAllCategoriesOfRestaurant(long restaurantId) {

        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", restaurantId)));

        restaurant.getCategories().clear();
        restaurantRepository.save(restaurant);

        restaurantCache.deleteAllCategoriesOfRestaurant(restaurantId);
    }
}
