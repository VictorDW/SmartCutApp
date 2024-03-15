package com.smartcut.app.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public record UserUpdate(
    @NotNull(message = "{message.user.id}")
    @Max(value = 999, message = "{message.invalid.id}")
    Long id,

    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{message.special.character}")
    @Size(min = 4, max = 20, message = "{message.default.size}")
    String username,

    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z0-9@.]+$", message = "{message.special.character.password}")
    @Size(min = 4, max = 15, message = "{message.password.size}")
    String newPassword,

    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "{message.only.letters}")
    @Size(min = 4, max = 20, message = "{message.default.size}")
    String firstName,

    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "{message.only.letters}")
    @Size(min = 4, max = 20, message = "{message.lastname.size}")
    String lastName,

    @Email(message = "{message.bad.email}")
    @NotBlank(message = "{message.empty.field}")
    String email,

    @Pattern(regexp = "^\\d+$", message = "{message.only.numbers}")
    @Size(min = 8, max = 11, message = "{message.cedula.size}")
    @NotBlank(message = "{message.empty.field}")
    String cedula,

    @Pattern(regexp = "^\\d+$", message = "{message.only.numbers}")
    @Size(min = 10, max = 10, message = "{message.phone.size}")
    @NotBlank(message = "{message.empty.field}")
    String phone,

    @NotBlank(message = "{message.empty.field}")
    String address
) { }