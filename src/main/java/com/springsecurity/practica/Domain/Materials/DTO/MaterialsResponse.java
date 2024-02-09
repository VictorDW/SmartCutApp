package com.springsecurity.practica.Domain.Materials.DTO;

import com.springsecurity.practica.Domain.Supplier.DTO.SupplierResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MaterialsResponse(
        String code,
        SupplierResponse supplier,
        String name,
        String type,
        Float width,
        Float height,
        BigDecimal unitPrice,
        Integer quantity,
        LocalDateTime dataRegister
) {
}
