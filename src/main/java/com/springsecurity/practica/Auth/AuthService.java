package com.springsecurity.practica.Auth;

import org.springframework.stereotype.Service;

import com.springsecurity.practica.Jwt.AuthResponse;
import com.springsecurity.practica.Jwt.LoginRequest;
import com.springsecurity.practica.Jwt.RegisterRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        return null;
    }

}
