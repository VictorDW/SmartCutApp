package com.springsecurity.practica.User.DTO;

public record UserUpdate(
    Long id,
    String username,
    String newPassword,
    String firstName,
    String lastName,
    String email,
    String cedula,
    String phone,
    String address
) { }
