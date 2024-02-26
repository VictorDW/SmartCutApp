package com.smartcut.app.Domain.Supplier.DTO;

import java.time.LocalDateTime;

public record SupplierResponse(
        Long id,
        String firstName,
        String lastName,
        String cedula,
        String phone,
        String address,
        String dataRegister,
        String description
) {
}
