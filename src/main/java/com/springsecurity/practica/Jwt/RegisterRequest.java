package com.springsecurity.practica.Jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    
    private String usarname;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
}
