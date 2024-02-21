package com.smartcut.app.Jwt.DTO;

import com.smartcut.app.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private String token;
}
