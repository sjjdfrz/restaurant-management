package com.neshan.restaurantmanagement.runner;

import com.neshan.restaurantmanagement.model.Role;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class UserRunner implements CommandLineRunner {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        User user1 = User
                .builder()
                .firstName("Sajjad")
                .lastName("Farzane")
                .email("sajjadfarzane1@gmail.com")
                .password(passwordEncoder.encode("sajjad1234"))
                .role(Role.ADMIN)
                .active(true)
                .build();

        User user2 = User
                .builder()
                .firstName("Saleh")
                .lastName("Shakour")
                .email("salehshakour@gmail.com")
                .password(passwordEncoder.encode("saleh1234"))
                .role(Role.USER)
                .active(true)
                .build();

        User user3 = User
                .builder()
                .firstName("Soroosh")
                .lastName("Faal")
                .email("sorooshfaal@gmail.com")
                .password(passwordEncoder.encode("soroosh1234"))
                .role(Role.USER)
                .active(true)
                .build();

        User user4 = User
                .builder()
                .firstName("Abolfazl")
                .lastName("Mohajer")
                .email("abolfazlmohajer@gmail.com")
                .password(passwordEncoder.encode("abolfazl1234"))
                .role(Role.USER)
                .active(true)
                .build();

        userRepository.saveAll(List.of(user1, user2, user3, user4));
    }
}
