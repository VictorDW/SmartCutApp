package com.smartcut.app.Auth.DTO;

import com.smartcut.app.Domain.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{message.special.character}")
    @Size(min = 4, max = 20, message = "{message.default.size}")
    private String username;

    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z0-9@.]+$", message = "{message.special.character.password}")
    @Size(min = 4, max = 15, message = "{message.password.size}")
    private String password;

    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "{message.only.letters}")
    @Size(min = 4, max = 20, message = "{message.default.size}")
    private String firstName;

    @NotBlank(message = "${message.empty.field}")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "{message.only.letters}")
    @Size(min = 4, max = 20, message = "{message.lastname.size}")
    private String lastName;

    @Email(message = "{message.bad.email}")
    @NotBlank(message = "{message.empty.field}")
    private String email;

    @Pattern(regexp = "^\\d+$", message = "{message.only.numbers}")
    @Size(min = 8, max = 11, message = "{message.cedula.size}")
    @NotBlank(message = "{message.empty.field}")
    private String cedula;

    @Pattern(regexp = "^\\d+$", message = "{message.only.numbers}")
    @Size(min = 10, max = 10, message = "{message.phone.size}")
    @NotBlank(message = "{message.empty.field}")
    private String phone;

    @NotBlank(message = "{message.empty.field}")
    private String address;
    
    private Role role;
}
