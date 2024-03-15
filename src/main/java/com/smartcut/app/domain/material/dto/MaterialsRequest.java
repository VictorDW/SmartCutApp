package com.smartcut.app.domain.material.dto;

import com.smartcut.app.domain.material.util.DecimalPesos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;


import java.math.BigDecimal;

public record MaterialsRequest(

        @NotBlank(message = "{message.empty.field}")
        @Pattern(regexp = "^\\d+$", message = "{message.only.numbers}")
        @Size(min = 4, max = 4, message = "{message.code.size}")
        String code,

        @NotNull(message = "{message.required.idSupplier}")
        Long IDSupplier,

        @NotBlank(message = "{message.empty.field}")
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "{message.only.letters}")
        @Size(min = 4, max = 20, message = "{message.default.size}")
        String name,

        @NotBlank(message = "{message.empty.field}")
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "{message.only.letters}")
        @Size(min = 2, max = 10, message = "{message.type.size}")
        String type,

        @NotNull(message = "{message.material.width}")
        BigDecimal width,

        @NotNull(message = "{message.material.height}")
        BigDecimal height,

        @NotNull(message = "{message.price.material}")
        @DecimalPesos
        BigDecimal unitPrice,

        @NotNull(message = "{message.quantity.material}")
        @Min(value = 1, message = "{message.invalid.quantity}")
        @Max(value = 999, message = "{message.invalid.quantity}")
        Integer quantity
) {
}