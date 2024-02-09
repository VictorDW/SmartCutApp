package com.springsecurity.practica.Domain.Supplier.Service;

import com.springsecurity.practica.Domain.Supplier.DTO.SupplierRequest;
import com.springsecurity.practica.Domain.Supplier.DTO.SupplierResponse;
import com.springsecurity.practica.Domain.Supplier.DTO.SupplierUpdate;
import com.springsecurity.practica.Domain.Supplier.Entity.Supplier;

import java.util.List;

public interface ISupplierService {
    SupplierResponse create(SupplierRequest request);
    Supplier getSupplierById(Long id);
    SupplierResponse getById(Long id);
    List<SupplierResponse> getAll();
    SupplierResponse update(SupplierUpdate update);
    void delete(String cedula);
}
