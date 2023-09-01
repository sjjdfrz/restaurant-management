package com.neshan.restaurantmanagement.util;

public class AppConstants {
    public static final String DEFAULT_PAGE_NUMBER = "1";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";

    public static final String[] GET_UN_SECURED_URLs = {
            "/restaurants/**",
            "/restaurants/{restaurantId}/categories",
            "/categories/{categoryId}/items"
    };

    public static final String[] POST_UN_SECURED_URLs = {
            "/register",
            "/login"
    };

    public static final String[] GET_USER_SECURED_URLs = {
            "/my-carts/**",
            "/my-orders/**",
            "/me"
    };

    public static final String[] POST_USER_SECURED_URLs = {
            "/my-carts",
            "/carts/{cartId}/orders"
    };

    public static final String[] PATCH_USER_SECURED_URLs = {
            "/my-carts/**",
            "/update-me"
    };

    public static final String[] DELETE_USER_SECURED_URLs = {
            "/my-carts/**",
            "/delete-me"
    };

    public static final String[] GET_ADMIN_SECURED_URLs = {
            "/carts/**",
            "/orders/**",
            "/users/**",
            "/categories/**",
            "/items/**",
            "/sales-stats/**",
            "/top-items/**",
    };

    public static final String[] POST_ADMIN_SECURED_URLs = {
            "/restaurants",
            "/restaurants/{restaurantId}/categories",
            "/categories/{categoryId}/items"

    };

    public static final String[] PATCH_ADMIN_SECURED_URLs = {
            "/orders/**",
            "/restaurants/**",
            "/categories/**",
            "/items/**"

    };

    public static final String[] DELETE_ADMIN_SECURED_URLs = {
            "/orders/**",
            "/restaurants/**",
            "/users/**",
            "/categories/**",
            "/restaurants/{restaurantId}/categories",
            "/categories/{categoryId}/items",
            "/items/**"
    };
}
