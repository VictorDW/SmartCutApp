package com.smartcut.app.domain.person.entity;

import com.smartcut.app.domain.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @Column(nullable = false)
    protected String email;

    @Column(nullable = false)
    protected String cedula;

    @Column(nullable = false)
    protected String phone;

    @Column(nullable = false)
    protected String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Status status;

    @Column(name = "data_register", nullable = false)
    protected LocalDateTime dateRegister;
}
