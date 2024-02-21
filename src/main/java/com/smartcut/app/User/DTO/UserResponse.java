package com.smartcut.app.User.DTO;

import com.smartcut.app.Domain.Status;
import com.smartcut.app.User.Role;

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
