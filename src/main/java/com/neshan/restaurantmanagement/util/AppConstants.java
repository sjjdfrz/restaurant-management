package com.neshan.restaurantmanagement.util;

public class AppConstants {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";

    public static final String[] GET_UN_SECURED_URLs = {
            "/menus/**",
            "/restaurants/**",
    };

    public static final String[] POST_UN_SECURED_URLs = {
            "/register",
            "/login"
    };

    public static final String[] GET_ADMIN_SECURED_URLs = {
            "/orders/**",
            "/menu-items/**",
            "/order-items/**",
            "/users/**",
    };

    public static final String[] POST_ADMIN_SECURED_URLs = {
            "/menus",
            "/menu-items",
            "/restaurants",
    };

    public static final String[] PATCH_ADMIN_SECURED_URLs = {
            "/menus/**",
            "/menu-items/**",
            "/restaurants/**",
            "/orders/**",
    };

    public static final String[] DELETE_ADMIN_SECURED_URLs = {
            "/menus/**",
            "/menu-items/**",
            "/restaurants/**",
            "/users/**",
    };
}
