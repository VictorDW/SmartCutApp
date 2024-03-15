package com.smartcut.app.domain.material.dto;

import com.smartcut.app.domain.supplier.dto.SupplierResponseBasic;

import java.math.BigDecimal;

public record MaterialsResponse(
        Long id,
        String code,
        SupplierResponseBasic supplier,
        String name,
        String type,
        BigDecimal width,
        BigDecimal height,
        String unitPrice,
        Integer quantity,
        String dataRegister
) {
}
