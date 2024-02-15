package com.springsecurity.demo.Domain.Materials.DTO;

import com.springsecurity.demo.Domain.Supplier.DTO.SupplierResponseBasic;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MaterialsResponse(
        Long id,
        String code,
        SupplierResponseBasic supplier,
        String name,
        String type,
        Float width,
        Float height,
        BigDecimal unitPrice,
        Integer quantity,
        LocalDateTime dataRegister
) {
}
