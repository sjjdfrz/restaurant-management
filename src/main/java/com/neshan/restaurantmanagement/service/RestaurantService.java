package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.cache.RestaurantCache;
import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.RestaurantMapper;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.model.dto.RestaurantsDto;
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
    private RestaurantCache restaurantCache;

    @Transactional
    public List<RestaurantsDto> getAllRestaurants(int pageNo, int pageSize, String sortBy) {

        if (restaurantCache.getRestaurants().isEmpty()) {

            List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
            Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

            return restaurantRepository
                    .findAll(paging)
                    .map(restaurant -> restaurantMapper.restaurantToRestaurantsDto(restaurant))
                    .getContent();
        }

        return restaurantCache
                .getRestaurants()
                .values()
                .stream()
                .map(restaurant -> restaurantMapper.restaurantToRestaurantsDto(restaurant))
                .toList();
    }

    @Transactional
    public RestaurantDto getRestaurant(long id) {

        if (restaurantCache.getRestaurants().isEmpty()) {

            Restaurant restaurant = restaurantRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementFoundException(
                            String.format("The restaurant with ID %d was not found.", id)));

            return restaurantMapper.restaurantToRestaurantDto(restaurant);
        }

        return restaurantMapper
                .restaurantToRestaurantDto(restaurantCache.getRestaurants().get(id));
    }

    @Transactional
    public void createRestaurant(RestaurantsDto restaurantsDto) {

        Restaurant restaurant = restaurantMapper.restaurantsDtoToRestaurant(restaurantsDto);
        restaurantRepository.saveAndFlush(restaurant);

        restaurantCache.add(restaurant);
    }

    @Transactional
    public void updateRestaurant(long id, RestaurantsDto restaurantRequest) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));

        restaurantMapper.updateRestaurantFromDto(restaurantRequest, restaurant);
        restaurantRepository.save(restaurant);

        restaurantCache.update(id, restaurant);
    }

    @Transactional
    public void deleteRestaurant(long id) {
        restaurantRepository.deleteById(id);
        restaurantCache.delete(id);
    }

    @Transactional
    public void deleteAllRestaurants() {
        restaurantRepository.deleteAll();
        restaurantCache.deleteAll();
    }
}
