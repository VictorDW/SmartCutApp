package com.smartcut.app.Domain.Supplier.Service;

import com.smartcut.app.Domain.Status;
import com.smartcut.app.Domain.Supplier.DTO.SupplierRequest;
import com.smartcut.app.Domain.Supplier.DTO.SupplierResponse;
import com.smartcut.app.Domain.Supplier.DTO.SupplierUpdate;
import com.smartcut.app.Domain.Supplier.Entity.Supplier;

import java.util.List;

public interface ISupplierService {
    SupplierResponse create(SupplierRequest request);
    Supplier getSupplier(Long id);
    SupplierResponse getSupplierByCedula(String cedula);
    List<SupplierResponse> getAll(Status status);
    SupplierResponse update(SupplierUpdate update);
    void changeSupplierStatus(Long id);
}
