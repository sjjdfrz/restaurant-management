package com.neshan.restaurantmanagement.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaginationSorting {

    public static Sort.Direction getSortDirection(String sort) {
        return sort.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    public static String getSortProperty(String sort) {
        return sort.startsWith("-") ? sort.substring(1) : sort;
    }

    public static List<Sort.Order> getOrders(String sortBy) {

        String[] sortParams = sortBy.split(",");
        List<Sort.Order> orders = new ArrayList<>();
        Arrays.stream(sortParams)
                .forEach(sort -> orders.add(new Sort.Order(getSortDirection(sort), getSortProperty(sort))));
        return orders;
    }

    public static Pageable getPaging(int pageNo, int pageSize, List<Sort.Order> orders) {
        return PageRequest.of(pageNo, pageSize, Sort.by(orders));
    }
}
