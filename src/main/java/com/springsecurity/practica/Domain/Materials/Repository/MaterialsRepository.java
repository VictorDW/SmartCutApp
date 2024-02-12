package com.springsecurity.practica.Domain.Materials.Repository;

import com.springsecurity.practica.Domain.Materials.Entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialsRepository extends JpaRepository<Materials, Long> {
    @Query("SELECT m FROM Materials m WHERE m.id=:id AND NOT(m.status='INACTIVE')")
    Optional<Materials> findMaterialById(@Param("id") Long id);
    @Query("SELECT m FROM Materials m WHERE NOT(m.status='INACTIVE')")
    List<Materials> findAllMaterials();
    @Query("SELECT m FROM Materials m WHERE  m.code=:code AND NOT(m.status='INACTIVE')")
    Optional<List<Materials>> findMaterialsByCode(@Param("code") String code);
}
