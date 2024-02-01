package com.springsecurity.practica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import com.springsecurity.practica.User.UserRepository;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;

/**
 * Clase que permite configurar el manejo de los filtros de Spring Security
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
            .csrf(csrf ->
                csrf.disable())
            .authorizeHttpRequests(authRequestConfig ->
                authRequestConfig
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated())
            .sessionManagement(sessionConfig ->
                sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}
