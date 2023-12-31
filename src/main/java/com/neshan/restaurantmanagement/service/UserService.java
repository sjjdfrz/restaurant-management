package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.UserMapper;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.model.dto.UsersDto;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.UserRepository;
import com.neshan.restaurantmanagement.model.dto.RegisterRequest;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public List<UsersDto> getAllUsers(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        return userRepository
                .findAll(paging)
                .map(user -> userMapper.userToUsersDto(user))
                .getContent();
    }

    @Transactional
    public UserDto getUser(long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The user with ID %d was not found.", id)));

        return userMapper.userToUserDto(user);
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public ApiResponse<Object> updateMe(
            RegisterRequest updateRequest,
            User user) {

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

    @Transactional
    public ApiResponse<Object> deleteMe(User user) {

        user.setDeleted(true);
        userRepository.save(user);

        return ApiResponse
                .builder()
                .status("success")
                .build();
    }

    @Transactional
    public ApiResponse<UsersDto> getMe(User user) {

        UsersDto usersDto = userMapper.userToUsersDto(user);

        return ApiResponse
                .<UsersDto>builder()
                .status("success")
                .data(usersDto)
                .build();
    }
}
