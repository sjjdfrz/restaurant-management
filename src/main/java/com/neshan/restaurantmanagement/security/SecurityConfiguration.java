package com.neshan.restaurantmanagement.security;

import com.neshan.restaurantmanagement.util.AppConstants;
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

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, AppConstants.GET_UN_SECURED_URLs)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, AppConstants.POST_UN_SECURED_URLs)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, AppConstants.GET_USER_SECURED_URLs)
                        .hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST, AppConstants.POST_USER_SECURED_URLs)
                        .hasAuthority("USER")
                        .requestMatchers(HttpMethod.PATCH, AppConstants.PATCH_USER_SECURED_URLs)
                        .hasAuthority("USER")
                        .requestMatchers(HttpMethod.DELETE, AppConstants.DELETE_USER_SECURED_URLs)
                        .hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET, AppConstants.GET_ADMIN_SECURED_URLs)
                        .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, AppConstants.POST_ADMIN_SECURED_URLs)
                        .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, AppConstants.PATCH_ADMIN_SECURED_URLs)
                        .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, AppConstants.DELETE_ADMIN_SECURED_URLs)
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
}
