package com.smartcut.app.domain.supplier.entity;

import com.smartcut.app.domain.material.entity.Materials;
import com.smartcut.app.domain.person.entity.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.OneToMany;
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
