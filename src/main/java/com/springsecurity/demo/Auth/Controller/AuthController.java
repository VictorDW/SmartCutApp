package com.springsecurity.demo.Auth.Controller;

import com.springsecurity.demo.Auth.Service.AuthService;
import com.springsecurity.demo.Auth.DTO.LoginRequest;
import com.springsecurity.demo.Auth.DTO.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.demo.Jwt.DTO.AuthResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request){
        return new ResponseEntity<>(authService.login(request), HttpStatus.ACCEPTED);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request){
        authService.register(request);
        return new ResponseEntity<>("Registration Made", HttpStatus.CREATED);
    }
    
}
