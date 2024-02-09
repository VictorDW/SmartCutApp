package com.springsecurity.practica.Domain.Supplier.Entity;

import com.springsecurity.practica.Domain.Materials.Entity.Materials;
import com.springsecurity.practica.Domain.Person.Entity.Person;
import com.springsecurity.practica.Domain.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "supplier")
@PrimaryKeyJoinColumn(name = "supplier_id")
public class Supplier extends Person{

    private String description;

    @OneToMany(mappedBy = "supplier")
    private List<Materials> materials = new ArrayList<>();

}
