package com.smartcut.app.domain.supplier.dto;

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
