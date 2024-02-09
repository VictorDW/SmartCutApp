package com.springsecurity.practica.Domain.Materials.Repository;

import com.springsecurity.practica.Domain.Materials.Entity.Materials;
import com.springsecurity.practica.Domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialsRepository extends JpaRepository<Materials, Long> {

    Optional<Materials> findByIdAndStatusNot(Long id, Status status);
    List<Materials> findAllByStatusNot(Status status);
}
