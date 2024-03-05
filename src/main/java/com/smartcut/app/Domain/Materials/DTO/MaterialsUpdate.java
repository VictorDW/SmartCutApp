package com.smartcut.app.Domain.Materials.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MaterialsUpdate(
        @NotNull(message = "${message.material.id}")
        Long id,

        @NotBlank(message = "${message.empty.field}")
        @Pattern(regexp = "^\\d+$", message = "${message.only.numbers}")
        @Size(min = 4, max = 4, message = "${message.code.size}")
        String code,

        @NotNull(message = "${message.required.idSupplier}")
        Long IDSupplier,

        @NotBlank(message = "${message.empty.field}")
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "${message.only.letters}")
        @Size(min = 4, max = 20, message = "${message.default.size}")
        String name,

        @NotBlank(message = "${message.empty.field}")
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "${message.only.letters}")
        @Size(min = 2, max = 10, message = "${message.type.size}")
        String type,

        @NotNull(message = "${message.material.width}")
        Float width,

        @NotNull(message = "${message.material.height}")
        Float height,

        @NotNull(message = "${message.price.material}")
        @DecimalMin(value = "0.00", message = "${Error en el formato del precio}")
        @DecimalMax(value = "999.999", message = "${Error en el formato del precio}")
        BigDecimal unitPrice,

        @NotNull(message = "${message.quantity.material}")
        @Min(value = 1, message = "${message.invalid.quantity}")
        @Max(value = 999, message = "${message.invalid.quantity}")
        Integer quantity
) {
}
