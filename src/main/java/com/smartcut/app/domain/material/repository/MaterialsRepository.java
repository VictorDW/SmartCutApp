package com.smartcut.app.domain.material.repository;

import com.smartcut.app.domain.material.entity.Materials;
import com.smartcut.app.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialsRepository extends JpaRepository<Materials, Long> {
    @Query("SELECT m FROM Materials m WHERE m.id=:id")
    Optional<Materials> findMaterialById(@Param("id") Long id);
    @Query("SELECT m FROM Materials m WHERE m.status=:status")
    List<Materials> findAllMaterials(@Param("status") Status status);
    @Query("SELECT m FROM Materials m WHERE  m.code=:code AND NOT(m.status='INACTIVE')")
    Optional<List<Materials>> findMaterialsByCode(@Param("code") String code);
}
