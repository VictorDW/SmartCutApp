package com.smartcut.app.Domain.User.DTO;

import com.smartcut.app.Domain.Status;
import com.smartcut.app.Domain.User.Role;

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
        String dataRegister,
        Status status
) {
}
