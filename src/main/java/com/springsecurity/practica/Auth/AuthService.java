package com.springsecurity.practica.Auth;

import com.springsecurity.practica.Jwt.JwtService;
import com.springsecurity.practica.User.Role;
import com.springsecurity.practica.User.User;
import com.springsecurity.practica.User.UserRepository;
import org.springframework.stereotype.Service;

import com.springsecurity.practica.Jwt.AuthResponse;
import com.springsecurity.practica.Jwt.LoginRequest;
import com.springsecurity.practica.Jwt.RegisterRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        return null;
    }
    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsarname())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .country(request.getCountry())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
    }

}
