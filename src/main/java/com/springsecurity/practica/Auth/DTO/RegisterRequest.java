package com.springsecurity.practica.Auth.DTO;

import com.springsecurity.practica.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private Role role;
}
