package com.neshan.restaurantmanagement.security;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.Role;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ApiResponse<Object> register(RegisterRequest request) {

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
}
