package com.springsecurity.demo.Domain.Supplier.Repository;

import com.springsecurity.demo.Domain.Status;
import com.springsecurity.demo.Domain.Supplier.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("SELECT s FROM Supplier s WHERE s.cedula= :cedula")
    Optional<Supplier> findByCedula(@Param("cedula") String Cedula);

    @Query("SELECT s FROM Supplier s WHERE s.id= :id")
    Optional<Supplier> findBySupplierId(@Param("id") Long id);

    @Query("SELECT s FROM Supplier s WHERE s.status=:status")
    List<Supplier> findAllSupplier(@Param("status") Status status);
}
