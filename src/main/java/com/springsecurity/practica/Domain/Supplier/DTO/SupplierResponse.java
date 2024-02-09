package com.springsecurity.practica.Domain.Supplier.DTO;

import java.time.LocalDateTime;

public record SupplierResponse(
        String firstName,
        String lastName,
        String cedula,
        String phone,
        String addres,
        LocalDateTime dataRegister,
        String description
) {
}
