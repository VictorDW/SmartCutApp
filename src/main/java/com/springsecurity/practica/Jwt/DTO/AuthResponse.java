package com.springsecurity.practica.Jwt.DTO;

import com.springsecurity.practica.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    Long id;
    String firstName;
    String lastName;
    Role role;
    private String token;
}
