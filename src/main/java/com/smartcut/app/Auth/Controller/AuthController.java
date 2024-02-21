package com.smartcut.app.Auth.Controller;

import com.smartcut.app.Auth.Service.AuthService;
import com.smartcut.app.Auth.DTO.LoginRequest;
import com.smartcut.app.Auth.DTO.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcut.app.Jwt.DTO.AuthResponse;

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
