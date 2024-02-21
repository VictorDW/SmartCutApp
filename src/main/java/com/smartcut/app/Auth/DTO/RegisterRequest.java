package com.smartcut.app.Auth.DTO;

import com.smartcut.app.User.Role;
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

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "No debe contener caracteres especiales")
    @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
    private String username;

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z0-9@.]+$", message = "No debe contener caracteres especiales diferentes al @ y el .")
    @Size(min = 4, max = 15, message = "Debe contener minimo 4 caracteres y maximo 15")
    private String password;

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Solo debe contener letras")
    @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
    private String firstName;

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Solo debe contener letras")
    @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 30")
    private String lastName;

    @Email
    @NotBlank(message = "El campo no puede estar vacio")
    private String email;

    @Pattern(regexp = "^\\d+$", message = "Solo debe contener solo numeros")
    @Size(min = 8, max = 11, message = "Debe contener minimo 8 digitos y maximo 11")
    @NotBlank(message = "El campo no puede estar vacio")
    private String cedula;

    @Pattern(regexp = "^\\d+$", message = "Solo debe contener solo numeros")
    @Size(min = 10, max = 10, message = "Error en el formato del numero de celular")
    @NotBlank(message = "El campo no puede estar vacio")
    private String phone;

    @NotBlank(message = "El campo no puede estar vacio")
    private String address;
    
    private Role role;
}
