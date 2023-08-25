package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.mapper.RestaurantMapper;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;

    public ApiResponse<Page<RestaurantDto>> getAllRestaurants(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable paging = PageRequest.of(pageNo, pageSize, sort);
        Page<RestaurantDto> pagedResult = restaurantRepository
                .findAll(paging)
                .map(restaurant -> restaurantMapper.restaurantToRestaurantDto(restaurant));

        return ApiResponse
                .<Page<RestaurantDto>>builder()
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
