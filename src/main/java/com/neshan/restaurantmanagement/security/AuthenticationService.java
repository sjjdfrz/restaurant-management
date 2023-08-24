package com.neshan.restaurantmanagement.security;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.UserMapper;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.Role;
import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    public ApiResponse<Object> register(RegisterRequest request, HttpServletResponse response) {

        User user = User
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return ApiResponse
                .builder()
                .status("success")
                .token(jwtToken)
                .build();
    }

    public ApiResponse<Object> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new NoSuchElementFoundException("Invalid user credentials!"));

        String jwtToken = jwtService.generateToken(user);
        return ApiResponse
                .builder()
                .status("success")
                .token(jwtToken)
                .build();
    }

    public ApiResponse<Object> update(RegisterRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) this.userDetailsService.loadUserByUsername(authentication.getName());

        userMapper.updateUserFromDto(request, user);
        userRepository.save(user);

        return ApiResponse
                .builder()
                .status("success")
                .message("Your information was updated successfully.")
                .build();
    }

    public ApiResponse<Object> delete() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) this.userDetailsService.loadUserByUsername(authentication.getName());

        user.setActive(false);
        userRepository.save(user);

        return ApiResponse
                .builder()
                .status("success")
                .build();
    }

    public ApiResponse<UserDto> getMe() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) this.userDetailsService.loadUserByUsername(authentication.getName());

        UserDto userDto = userMapper.userToUserDto(user);

        return ApiResponse
                .<UserDto>builder()
                .status("success")
                .data(userDto)
                .build();
    }
}
