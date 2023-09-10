package com.neshan.restaurantmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neshan.restaurantmanagement.amqp.RabbitMQMessageProducer;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;
    private RestaurantCache restaurantCache;
    private RabbitMQMessageProducer rabbitMQMessageProducer;

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
    public RestaurantDto getRestaurant(long id) throws JsonProcessingException {

        if (restaurantCache.getRestaurants().isEmpty()) {

            Restaurant restaurant = restaurantRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementFoundException(
                            String.format("The restaurant with ID %d was not found.", id)));


            String log = String.format("%s Restaurant with id: %d was seen in %s",
                    restaurant.getName(),
                    restaurant.getId(),
                    LocalDateTime.now());

            ObjectMapper objectMapper = new ObjectMapper();
            byte[] payloadBytes = objectMapper.writeValueAsBytes(log);

            rabbitMQMessageProducer.publish(
                    "internal.exchange",
                    "internal.logger.routing-key",
                    payloadBytes
            );

            return restaurantMapper.restaurantToRestaurantDto(restaurant);
        }

        Restaurant restaurant = restaurantCache.getRestaurants().get(id);

        String log = String.format("%s Restaurant with id: %d was seen in %s",
                restaurant.getName(),
                restaurant.getId(),
                LocalDateTime.now());

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] payloadBytes = objectMapper.writeValueAsBytes(log);

        rabbitMQMessageProducer.publish(
                "internal.exchange",
                "internal.logger.routing-key",
                payloadBytes
        );

        return restaurantMapper
                .restaurantToRestaurantDto(restaurant);
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
