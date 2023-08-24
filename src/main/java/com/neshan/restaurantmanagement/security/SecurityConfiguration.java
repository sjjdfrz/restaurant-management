package com.neshan.restaurantmanagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                .logout(logout -> logout.logoutUrl("/logout"))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
