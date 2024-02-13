package com.springsecurity.demo.User.DTO;

import com.springsecurity.demo.Domain.Status;
import com.springsecurity.demo.User.Role;

import java.time.LocalDateTime;

public record UserResponse(

        Long id,
        String username,
        Role role,
        String firstName,
        String LastName,
        String cedula,
        String email,
        String phone,
        String address,
        LocalDateTime dataRegister,
        Status status
) {
}
