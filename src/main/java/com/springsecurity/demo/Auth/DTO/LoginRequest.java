package com.springsecurity.demo.Auth.DTO;

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

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "No debe contener caracteres especiales")
    @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
    private String username;

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z0-9@.]+$", message = "No debe contener caracteres especiales diferentes al @ y el .")
    @Size(min = 4, max = 15, message = "Debe contener minimo 4 caracteres y maximo 15")
    private String password;
}
