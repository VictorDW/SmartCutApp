package com.springsecurity.demo.Domain.Supplier.Entity;

import com.springsecurity.demo.Domain.Materials.Entity.Materials;
import com.springsecurity.demo.Domain.Person.Entity.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
