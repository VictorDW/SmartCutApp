package com.smartcut.app.Domain.Supplier.DTO;

import java.time.LocalDateTime;

public record SupplierResponse(
        Long id,
        String firstName,
        String lastName,
        String cedula,
        String phone,
        String addres,
        LocalDateTime dataRegister,
        String description
) {
}
