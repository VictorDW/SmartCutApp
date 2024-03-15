package com.smartcut.app.domain.supplier.service;

import com.smartcut.app.domain.Status;
import com.smartcut.app.domain.supplier.dto.SupplierRequest;
import com.smartcut.app.domain.supplier.dto.SupplierResponse;
import com.smartcut.app.domain.supplier.dto.SupplierUpdate;
import com.smartcut.app.domain.supplier.entity.Supplier;

import java.util.List;

public interface ISupplierService {
    SupplierResponse create(SupplierRequest request);
    Supplier getSupplier(Long id);
    SupplierResponse getSupplierByCedula(String cedula);
    List<SupplierResponse> getAll(Status status);
    SupplierResponse update(SupplierUpdate update);
    void changeSupplierStatus(Long id);
}
