package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.RestaurantMapper;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import com.neshan.restaurantmanagement.repository.RestaurantRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;

    @Transactional
    public List<RestaurantDto> getAllRestaurants(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        return restaurantRepository
                .findAll(paging)
                .map(restaurant -> restaurantMapper.restaurantToRestaurantDto(restaurant))
                .getContent();
    }

    @Transactional
    public RestaurantDto getRestaurant(long id) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));

        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    @Transactional
    public Restaurant getRestaurantEntityById(long id) {

        return restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));
    }

    @Transactional
    public void createRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantMapper.restaurantDtoToRestaurant(restaurantDto);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void updateRestaurant(long id, RestaurantDto restaurantRequest) {

        Restaurant restaurant = getRestaurantEntityById(id);

        restaurantMapper.updateRestaurantFromDto(restaurantRequest, restaurant);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void deleteRestaurant(long id) {
        restaurantRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllRestaurants() {
        restaurantRepository.deleteAll();
    }
}
