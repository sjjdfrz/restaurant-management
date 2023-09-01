package com.neshan.restaurantmanagement.interceptor;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).addPathPatterns(
                "/my-carts/**",
                "/my-orders/**",
                "/carts/{cartId}/orders",
                "/update-me",
                "/delete-me",
                "/me"
        );
    }
}
