package com.springsecurity.practica.Domain.Supplier.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierUpdate(

        @NotNull(message = "Se requiere el id del proveedor")
        Long id,

        @NotBlank(message = "El campo no puede estar vacio")
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Solo debe contener letras")
        @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
        String firstName,

        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Solo debe contener letras")
        @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
        @NotBlank(message = "El campo no puede estar vacio")
        String lastName,

        @Pattern(regexp = "^\\d+$", message = "Solo debe contener solo numeros")
        @Size(min = 8, max = 11, message = "Debe contener minimo 8 digitos y maximo 11")
        @NotBlank(message = "El campo no puede estar vacio")
        String cedula,

        @Pattern(regexp = "^\\d+$", message = "Solo debe contener solo numeros")
        @Size(min = 10, max = 10, message = "Error en el formato del numero de celular")
        @NotBlank(message = "El campo no puede estar vacio")
        String phone,

        @NotBlank(message = "El campo no puede estar vacio")
        String address,
        String description
) {
}
