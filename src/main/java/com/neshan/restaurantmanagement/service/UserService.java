package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.UserRepository;
import com.neshan.restaurantmanagement.mapper.UserMapper;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public ApiResponse<List<UserDto>> getAllUsers(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        List<UserDto> pagedResult = userRepository
                .findAll(paging)
                .map(user -> userMapper.userToUserDto(user));

        return ApiResponse
                .<List<UserDto>>builder()
                .status("success")
                .data(pagedResult)
                .build();
    }

    public ApiResponse<UserDto> getUserById(long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The user with ID %d was not found.", id)));

        UserDto userDto = userMapper.userToUserDto(user);
        return ApiResponse
                .<UserDto>builder()
                .status("success")
                .data(userDto)
                .build();
    }

    public ApiResponse<Object> deleteUser(long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The user with ID %d was not found.", id)));

        userRepository.delete(user);

        return ApiResponse
                .builder()
                .status("success")
                .message("User was deleted successfully.")
                .build();
    }
}
