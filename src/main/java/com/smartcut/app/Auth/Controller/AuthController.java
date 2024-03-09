package com.smartcut.app.Auth.Controller;

import com.smartcut.app.Auth.Service.AuthService;
import com.smartcut.app.Auth.DTO.LoginRequest;
import com.smartcut.app.Auth.DTO.RegisterRequest;
import com.smartcut.app.Util.MessageComponent;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.smartcut.app.Jwt.DTO.AuthResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MessageComponent messageComponent;

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
