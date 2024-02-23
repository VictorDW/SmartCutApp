package com.smartcut.app.User.DTO;

import jakarta.validation.constraints.*;

public record UserUpdate(
    @NotNull(message = "Se requiere el id del usuario")
    Long id,

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "No debe contener caracteres especiales")
    @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
    String username,

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z0-9@.]+$", message = "No debe contener caracteres especiales diferentes al @ y el .")
    @Size(min = 4, max = 15, message = "Debe contener minimo 4 caracteres y maximo 15")
    String newPassword,

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Solo debe contener letras")
    @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
    String firstName,

    @NotBlank(message = "El campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Solo debe contener letras")
    @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 30")
    String lastName,

    @Email
    @NotBlank(message = "El campo no puede estar vacio")
    String email,

    @Pattern(regexp = "^\\d+$", message = "Solo debe contener numeros")
    @Size(min = 8, max = 11, message = "Debe contener minimo 8 digitos y maximo 11")
    @NotBlank(message = "El campo no puede estar vacio")
    String cedula,

    @Pattern(regexp = "^\\d+$", message = "Solo debe contener numeros")
    @Size(min = 10, max = 10, message = "Error en el formato del numero de celular")
    @NotBlank(message = "El campo no puede estar vacio")
    String phone,

    @NotBlank(message = "El campo no puede estar vacio")
    String address
) { }
