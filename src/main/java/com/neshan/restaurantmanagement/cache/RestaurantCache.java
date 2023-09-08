package com.neshan.restaurantmanagement.cache;

import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
public class RestaurantCache {

    private RedissonClient redisClient;
    private Map<Long, RestaurantDto> restaurants;

    public RestaurantCache(RedissonClient redisClient) {
        this.redisClient = redisClient;
    }

    @PostConstruct
    public void init() {

        restaurants = redisClient.getMap(
                "restaurants",
                new TypedJsonJacksonCodec(Long.class, RestaurantDto.class)
        );
    }

    public void update(long id, RestaurantDto restaurantDto) {
        restaurants.replace(id, restaurantDto);
    }

    public void add(RestaurantDto restaurantDto) {
        restaurants.put(restaurantDto.id(), restaurantDto);
    }

    public void delete(long id) {
        restaurants.remove(id);
    }

    public void deleteAll() {
        restaurants.clear();
    }
}
