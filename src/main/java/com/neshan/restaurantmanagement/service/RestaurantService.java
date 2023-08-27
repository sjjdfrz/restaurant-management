package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.RestaurantMapper;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.repository.RestaurantRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;

    public ApiResponse<List<RestaurantDto>> getAllRestaurants(int pageNo, int pageSize, String sortBy) {

        List<Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        List<RestaurantDto> pagedResult = restaurantRepository
                .findAll(paging)
                .map(restaurant -> restaurantMapper.restaurantToRestaurantDto(restaurant))
                .getContent();

        return ApiResponse
                .<List<RestaurantDto>>builder()
                .status("success")
                .data(pagedResult)
                .build();
    }

    public ApiResponse<RestaurantDto> getRestaurantById(long id) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));

        RestaurantDto restaurantDto = restaurantMapper.restaurantToRestaurantDto(restaurant);
        return ApiResponse
                .<RestaurantDto>builder()
                .status("success")
                .data(restaurantDto)
                .build();
    }

    public ApiResponse<Object> createRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantMapper.restaurantDtoToRestaurant(restaurantDto);
        restaurantRepository.save(restaurant);

        return ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was created successfully.")
                .build();
    }

    public ApiResponse<Object> updateRestaurant(long id, RestaurantDto restaurantRequest) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));

        restaurantMapper.updateRestaurantFromDto(restaurantRequest, restaurant);
        restaurantRepository.save(restaurant);

        return ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was updated successfully.")
                .build();
    }

    public ApiResponse<Object> deleteRestaurant(long id) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));

        restaurantRepository.delete(restaurant);

        return ApiResponse
                .builder()
                .status("success")
                .message("Restaurant was deleted successfully.")
                .build();
    }
}
