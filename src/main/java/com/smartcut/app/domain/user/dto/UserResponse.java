package com.smartcut.app.domain.user.dto;

import com.smartcut.app.domain.Status;
import com.smartcut.app.domain.user.Role;

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
