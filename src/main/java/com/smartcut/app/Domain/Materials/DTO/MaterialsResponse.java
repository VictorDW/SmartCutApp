package com.smartcut.app.Domain.Materials.DTO;

import com.smartcut.app.Domain.Supplier.DTO.SupplierResponseBasic;

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
        String unitPrice,
        Integer quantity,
        String dataRegister
) {
}
