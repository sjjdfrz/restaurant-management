package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.CategoryMapper;
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
    private RestaurantService restaurantService;
    private RestaurantRepository restaurantRepository;

    @Transactional
    public List<CategoryDto> getAllCategories(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        return categoryRepository
                .findAll(paging)
                .map(category -> categoryMapper.categoryToCategoryDto(category))
                .getContent();
    }

    @Transactional
    public CategoryDto getCategory(long id) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The category with ID %d was not found.", id)));

        return categoryMapper.categoryToCategoryDto(category);
    }

    @Transactional
    public Category getCategoryEntityById(long id) {

        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The category with ID %d was not found.", id)));
    }

    @Transactional
    public List<CategoryDto> getAllCategoriesOfRestaurant(Long restaurantId) {
        return restaurantService
                .getRestaurant(restaurantId)
                .categories();
    }

    @Transactional
    public void createCategory(long restaurantId, CategoryDto categoryDto) {

        Restaurant restaurant = restaurantService.getRestaurantEntityById(restaurantId);

        Category category = categoryMapper.categoryDtoToCategory(categoryDto);
        restaurant.getCategories().add(category);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void updateCategory(long id, CategoryDto categoryRequest) {

        Category category = getCategoryEntityById(id);

        categoryMapper.updateCategoryFromDto(categoryRequest, category);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

    @Transactional
    public void deleteAllCategoriesOfRestaurant(long restaurantId) {

        Restaurant restaurant = restaurantService.getRestaurantEntityById(restaurantId);

        restaurant.getCategories().clear();
        restaurantRepository.save(restaurant);
    }
}
