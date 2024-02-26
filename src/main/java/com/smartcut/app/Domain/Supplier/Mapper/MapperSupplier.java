package com.smartcut.app.Domain.Supplier.Mapper;

import com.smartcut.app.Domain.Status;
import com.smartcut.app.Domain.Supplier.DTO.SupplierRequest;
import com.smartcut.app.Domain.Supplier.DTO.SupplierResponse;
import com.smartcut.app.Domain.Supplier.DTO.SupplierUpdate;
import com.smartcut.app.Domain.Supplier.Entity.Supplier;
import com.smartcut.app.Util.DateUtils;

import java.time.LocalDateTime;

public class MapperSupplier {

    private MapperSupplier() {}

    public static Supplier mapperSupplierRequestToSupplier(SupplierRequest request) {

        Supplier supplier = new Supplier();

        supplier.setFirstName(request.firstName());
        supplier.setLastName(request.lastName());
        supplier.setCedula(request.cedula());
        supplier.setEmail(request.email());
        supplier.setPhone(request.phone());
        supplier.setAddress(request.address());
        supplier.setDescription(request.description());
        supplier.setStatus(Status.ACTIVE);
        supplier.setDateRegister(LocalDateTime.now());

        return supplier;
    }

    public static SupplierResponse mapperSuppliertToSupplierResponse(Supplier supplier) {

        return new SupplierResponse(
                supplier.getId(),
                supplier.getFirstName(),
                supplier.getLastName(),
                supplier.getCedula(),
                supplier.getPhone(),
                supplier.getAddress(),
                DateUtils.dateFormat(supplier.getDateRegister()),
                supplier.getDescription()
        );
    }

    public static  Supplier mapperSupplierUpdate(Supplier supplier, SupplierUpdate update) {

        supplier.setFirstName(update.firstName());
        supplier.setLastName(update.lastName());
        supplier.setCedula(update.cedula());
        supplier.setPhone(update.phone());
        supplier.setAddress(update.address());
        supplier.setDescription(update.description());

        return supplier;
    }

    public static Supplier mapperStatus(Supplier supplier) {
        Status newStatusSupplier = (supplier.getStatus().equals(Status.ACTIVE)) ? Status.INACTIVE : Status.ACTIVE;
        supplier.setStatus(newStatusSupplier);
        return supplier;
    }
}
