package com.neshan.restaurantmanagement.cache;

import com.neshan.restaurantmanagement.model.entity.Restaurant;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class RestaurantCache {

    private final RedissonClient redisClient;
    private Map<Long, Restaurant> restaurants;

    @PostConstruct
    public void init() {

        restaurants = redisClient.getMap(
                "restaurants",
                new TypedJsonJacksonCodec(Long.class, Restaurant.class)
        );
    }

    public void add(Restaurant restaurant) {
        restaurants.put(restaurant.getId(), restaurant);
    }

    public void update(long id, Restaurant restaurant) {
        restaurants.replace(id, restaurant);
    }

    public void delete(long id) {
        restaurants.remove(id);
    }

    public void deleteAll() {
        restaurants.clear();
    }

    public void deleteAllCategoriesOfRestaurant(long restaurantId) {

        restaurants
                .get(restaurantId)
                .getCategories()
                .clear();
    }
}
