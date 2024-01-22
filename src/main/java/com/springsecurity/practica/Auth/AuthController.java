package com.springsecurity.practica.Auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("login")
    public String login(){
        return "Login from public endpoint";
    }

    @PostMapping("register")
    public String register(){
        return "Register form public endpoint";
    }
    
}
