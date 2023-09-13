package com.neshan.restaurantmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neshan.restaurantmanagement.model.dto.RestaurantDto;
import com.neshan.restaurantmanagement.model.dto.RestaurantsDto;
import com.neshan.restaurantmanagement.model.entity.Restaurant;
import com.neshan.restaurantmanagement.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void createRestaurant() throws Exception {

        //given
        RestaurantsDto restaurantsDto = RestaurantsDto
                .builder()
                .name("pizza restaurant")
                .build();

        //when
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(restaurantsDto))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer <valid-jwt-token>"))
                .andExpect(status().isOk());

        //then
        ArgumentCaptor<RestaurantsDto> restaurantCaptor = ArgumentCaptor.forClass(RestaurantsDto.class);
        verify(restaurantService, times(1)).createRestaurant(restaurantCaptor.capture());
        assertThat(restaurantCaptor.getValue().getName()).isEqualTo("pizza restaurant");
    }
}