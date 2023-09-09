package com.neshan.restaurantmanagement.cache;

import com.neshan.restaurantmanagement.model.entity.Category;
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
public class CategoryCache {

    private final RedissonClient redisClient;
    private Map<Long, Category> categories;

    @PostConstruct
    public void init() {

        categories = redisClient.getMap(
                "categories",
                new TypedJsonJacksonCodec(Long.class, Category.class)
        );
    }

    public void add(Category category) {
        categories.put(category.getId(), category);
    }

    public void update(long id, Category category) {
        categories.replace(id, category);
    }

    public void delete(long id) {
        categories.remove(id);
    }

    public void deleteAll() {
        categories.clear();
    }

}
