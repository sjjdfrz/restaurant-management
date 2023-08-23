package com.neshan.restuarantmanagement.service;

import com.neshan.restuarantmanagement.exception.NoSuchElementFoundException;

import com.neshan.restuarantmanagement.model.dto.RestaurantDto;
import com.neshan.restuarantmanagement.model.entity.Restaurant;
import com.neshan.restuarantmanagement.repository.RestaurantRepository;
import com.neshan.restuarantmanagement.util.RestuarantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private RestuarantMapper restuarantMapper;

    public List<RestaurantDto> getAllRestaurants() {

        return restaurantRepository
                .findAll()
                .stream()
                .map(restuarantMapper::restaurantToRestaurantDto)
                .toList();
    }

    public RestaurantDto getRestaurantById(long id) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));

        return restuarantMapper.restaurantToRestaurantDto(restaurant);
    }

    public void createRestaurant(RestaurantDto restaurantDto) {

        Restaurant restaurant = restuarantMapper.restaurantDtoToRestaurant(restaurantDto);
        restaurantRepository.save(restaurant);
    }

    public void updateRestaurant(long id, RestaurantDto restaurantRequest) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));

        restuarantMapper.updateRestaurantFromDto(restaurantRequest, restaurant);
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(long id) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The restaurant with ID %d was not found.", id)));

        restaurantRepository.delete(restaurant);
    }
}
