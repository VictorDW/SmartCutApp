package com.springsecurity.practica.Auth.DTO;

import com.springsecurity.practica.Domain.Status;
import com.springsecurity.practica.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String cedula;
    private String phone;
    private String address;
    private Role role;
}
