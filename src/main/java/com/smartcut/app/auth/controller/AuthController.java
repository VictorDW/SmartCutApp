package com.smartcut.app.auth.controller;

import com.smartcut.app.auth.service.AuthService;
import com.smartcut.app.auth.dto.LoginRequest;
import com.smartcut.app.auth.dto.RegisterRequest;
import com.smartcut.app.util.MessageUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smartcut.app.jwt.dto.AuthResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MessageUtil messageComponent;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request){
        return new ResponseEntity<>(authService.login(request), HttpStatus.ACCEPTED);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request){
        authService.register(request);
        return new ResponseEntity<>(messageComponent.getMessage("message.info.register"), HttpStatus.CREATED);
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return new ResponseEntity<>(messageComponent.getMessage("message.warn.logout"), HttpStatus.ACCEPTED);
    }
    
}
