package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.UserMapper;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.UserRepository;
import com.neshan.restaurantmanagement.security.RegisterRequest;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        return userRepository
                .findAll(paging)
                .map(user -> userMapper.userToUserDto(user))
                .getContent();
    }

    public UserDto getUser(long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The user with ID %d was not found.", id)));

        return userMapper.userToUserDto(user);
    }

    public void deleteUser(long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The user with ID %d was not found.", id)));

        userRepository.delete(user);
    }


    public ApiResponse<Object> updateMe(
            RegisterRequest updateRequest,
            HttpServletRequest httpRequest) {

        User user = (User) httpRequest.getAttribute("user");

        if (!updateRequest.password().isBlank())
            user.setPassword(passwordEncoder.encode(updateRequest.password()));

        userMapper.updateUserFromDto(updateRequest, user);
        userRepository.save(user);

        return ApiResponse
                .builder()
                .status("success")
                .message("Your profile was updated successfully.")
                .build();
    }

    public ApiResponse<Object> deleteMe(HttpServletRequest httpRequest) {

        User user = (User) httpRequest.getAttribute("user");

        user.setDeleted(true);
        userRepository.save(user);

        return ApiResponse
                .builder()
                .status("success")
                .build();
    }

    public ApiResponse<UserDto> getMe(HttpServletRequest httpRequest) {

        User user = (User) httpRequest.getAttribute("user");

        UserDto userDto = userMapper.userToUserDto(user);

        return ApiResponse
                .<UserDto>builder()
                .status("success")
                .data(userDto)
                .build();
    }
}
