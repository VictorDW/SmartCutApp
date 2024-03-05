package com.smartcut.app.Auth.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "${message.empty.field}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "${message.special.character}")
    @Size(min = 4, max = 20, message = "${message.default.size}")
    private String username;

    @NotBlank(message = "${message.empty.field}")
    @Pattern(regexp = "^[A-Za-z0-9@.]+$", message = "${message.special.character.password}")
    @Size(min = 4, max = 15, message = "${message.password.size}")
    private String password;
}
