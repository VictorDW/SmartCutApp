package com.springsecurity.demo.Domain.Supplier.Service;

import com.springsecurity.demo.Domain.Status;
import com.springsecurity.demo.Domain.Supplier.DTO.SupplierRequest;
import com.springsecurity.demo.Domain.Supplier.DTO.SupplierResponse;
import com.springsecurity.demo.Domain.Supplier.DTO.SupplierUpdate;
import com.springsecurity.demo.Domain.Supplier.Entity.Supplier;

import java.util.List;

public interface ISupplierService {
    SupplierResponse create(SupplierRequest request);
    Supplier getSupplier(Long id);
    SupplierResponse getSupplierByCedula(String cedula);
    List<SupplierResponse> getAll(Status status);
    SupplierResponse update(SupplierUpdate update);
    void changeSupplierStatus(Long id);
}
