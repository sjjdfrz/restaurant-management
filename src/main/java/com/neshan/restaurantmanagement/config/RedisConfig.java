package com.neshan.restaurantmanagement.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean
    RedissonClient redisson() {
        return Redisson.create();
    }
}
