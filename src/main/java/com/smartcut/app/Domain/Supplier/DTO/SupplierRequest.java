package com.smartcut.app.Domain.Supplier.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierRequest(
    @NotBlank(message = "${message.empty.field}")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "${message.only.letters}")
    @Size(min = 4, max = 20, message = "${message.default.size}")
    String firstName,

    @NotBlank(message = "${message.empty.field}")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "${message.only.letters}")
    @Size(min = 4, max = 20, message = "${message.lastname.size}")
    String lastName,

    @Pattern(regexp = "^\\d+$", message = "${message.only.numbers}")
    @Size(min = 8, max = 11, message = "${message.cedula.size}")
    @NotBlank(message = "${message.empty.field}")
    String cedula,

    @Email(message = "${message.bad.email}")
    @NotBlank(message = "${message.empty.field}")
    String email,

    @Pattern(regexp = "^\\d+$", message = "${message.only.numbers}")
    @Size(min = 10, max = 10, message = "${message.phone.size}")
    @NotBlank(message = "${message.empty.field}")
    String phone,

    @NotBlank(message = "${message.empty.field}")
    String address,
    String description

) { }
