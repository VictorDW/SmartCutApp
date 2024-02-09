package com.springsecurity.practica.Domain.Materials.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MaterialsRequest(

        @NotBlank(message = "El campo no puede estar vacio")
        @Pattern(regexp = "^\\d+$", message = "Solo debe contener numeros")
        @Size(min = 4, max = 4, message = "Debe contener 4 digitos")
        String code,

        @NotNull(message = "Se requiere el id del proveedor")
        Long IDSupplier,

        @NotBlank(message = "El campo no puede estar vacio")
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Solo debe contener letras")
        @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
        String name,

        @NotBlank(message = "El campo no puede estar vacio")
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Solo debe contener letras")
        @Size(min = 2, max = 10, message = "Debe contener minimo 2 caracteres y maximo 10")
        String type,

        @NotNull(message = "Se requiere definir el ancho")
        Float width,

        @NotNull(message = "Se requiere definir el alto")
        Float height,

        @NotNull(message = "Se requiere definir el costo del material por unidad")
        @DecimalMin(value = "0.000", message = "Error en el formato del precio")
        @DecimalMax(value = "9999.9999", message = "Error en el formato del precio")
        BigDecimal unitPrice,

        @NotNull(message = "Se requiere definir la cantidad")
        @Min(value = 1, message = "Error en el formato de la cantidad")
        @Max(value = 999, message = "Error en el formato de la cantidad")
        Integer quantity
) {
}
