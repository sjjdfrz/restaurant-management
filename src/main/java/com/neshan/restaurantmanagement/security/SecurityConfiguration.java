package com.neshan.restaurantmanagement.security;

import com.neshan.restaurantmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] GET_UN_SECURED_URLs = {
            "/menus/**",
            "/restaurants/**",
    };

    private static final String[] POST_UN_SECURED_URLs = {
            "/register",
            "/login"
    };

    private static final String[] GET_ADMIN_SECURED_URLs = {
            "/orders/**",
            "/menu-items/**",
            "/order-items/**",
            "/users/**",
    };

    private static final String[] POST_ADMIN_SECURED_URLs = {
            "/menus",
            "/menu-items",
            "/restaurants",
    };

    private static final String[] PATCH_ADMIN_SECURED_URLs = {
            "/menus/**",
            "/menu-items/**",
            "/restaurants/**",
            "/orders/**",
    };

    private static final String[] DELETE_ADMIN_SECURED_URLs = {
            "/menus/**",
            "/menu-items/**",
            "/restaurants/**",
            "/users/**",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, GET_UN_SECURED_URLs)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, POST_UN_SECURED_URLs)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, GET_ADMIN_SECURED_URLs)
                        .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, POST_ADMIN_SECURED_URLs)
                        .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, PATCH_ADMIN_SECURED_URLs)
                        .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, DELETE_ADMIN_SECURED_URLs)
                        .hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
