package com.neshan.restaurantmanagement.shell;

import com.neshan.restaurantmanagement.cache.RestaurantCache;
import com.neshan.restaurantmanagement.mapper.RestaurantMapper;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class SSHCommand {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;
    private RestaurantCache restaurantCache;


    @ShellMethod(value = "connect to remote server")
    public void fillRestaurantsCache() {

        // Get restaurants from database
        List<RestaurantDto> restaurants = restaurantRepository
                .findAll()
                .stream()
                .map(restaurant -> restaurantMapper.restaurantToRestaurantDto(restaurant))
                .toList();

        // Fill redis cache with restaurants
        restaurants.forEach(restaurant -> restaurantCache
                .getRestaurants()
                .put(restaurant.id(), restaurant));
    }
}
