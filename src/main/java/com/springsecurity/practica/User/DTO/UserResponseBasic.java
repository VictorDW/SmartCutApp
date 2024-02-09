package com.springsecurity.practica.User.DTO;

import com.springsecurity.practica.User.Role;

public record UserResponseBasic(
        Long id,
        String firstName,
        String lastName,
        Role role
) {
}
