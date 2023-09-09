package com.neshan.restaurantmanagement.shell;

import com.neshan.restaurantmanagement.cache.CategoryCache;
import com.neshan.restaurantmanagement.cache.RestaurantCache;
import com.neshan.restaurantmanagement.model.entity.Category;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import com.neshan.restaurantmanagement.repository.CategoryRepository;
import com.neshan.restaurantmanagement.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class SSHCommand {

    private RestaurantRepository restaurantRepository;
    private CategoryRepository categoryRepository;
    private RestaurantCache restaurantCache;
    private CategoryCache categoryCache;


    @ShellMethod
    public void fillRestaurantsCache() {

        // Get restaurants from database
        List<Restaurant> restaurants = restaurantRepository.findAll();

        // Fill redis cache with restaurants
        restaurants.forEach(restaurant -> restaurantCache
                .getRestaurants()
                .put(restaurant.getId(), restaurant));
    }

    @ShellMethod
    public void fillCategoriesCache() {

        // Get categories from database
        List<Category> categories = categoryRepository.findAll();

        // Fill redis cache with categories
        categories.forEach(category -> categoryCache
                .getCategories()
                .put(category.getId(), category));
    }
}
