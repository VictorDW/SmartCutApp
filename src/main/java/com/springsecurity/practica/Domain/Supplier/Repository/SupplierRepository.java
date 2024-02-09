package com.springsecurity.practica.Domain.Supplier.Repository;

import com.springsecurity.practica.Domain.Status;
import com.springsecurity.practica.Domain.Supplier.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByCedulaAndStatusNot(String Cedula, Status status);
    Optional<Supplier> findByIdAndStatusNot(Long id, Status status);
    List<Supplier> findAllByStatusNot(Status status);
}
