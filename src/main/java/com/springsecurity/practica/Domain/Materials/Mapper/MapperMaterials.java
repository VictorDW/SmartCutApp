package com.springsecurity.practica.Domain.Materials.Mapper;

import com.springsecurity.practica.Domain.Materials.DTO.MaterialsRequest;
import com.springsecurity.practica.Domain.Materials.DTO.MaterialsResponse;
import com.springsecurity.practica.Domain.Materials.DTO.MaterialsUpdate;
import com.springsecurity.practica.Domain.Materials.Entity.Materials;
import com.springsecurity.practica.Domain.Status;
import com.springsecurity.practica.Domain.Supplier.DTO.SupplierResponse;
import com.springsecurity.practica.Domain.Supplier.Entity.Supplier;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class MapperMaterials{

    private MapperMaterials(){}

    public static Materials mapperMaterialsRequestToMaterials(MaterialsRequest request, Supplier supplier) {

        Materials materials = new Materials();

        materials.setCode(request.code());
        materials.setSupplier(supplier);
        materials.setName(request.name());
        materials.setType(request.type());
        materials.setWidth(request.width());
        materials.setHeight(request.height());
        materials.setUnitPrice(request.unitPrice());
        materials.setQuantity(request.quantity());
        materials.setDateRegister(LocalDateTime.now());
        materials.setStatus(Status.ACTIVE);

        return materials;
    }

    public static MaterialsResponse mapperMaterialsToMaterialsResponse(Materials materials) {

        var supplier = materials.getSupplier();

        return new MaterialsResponse(
                materials.getCode(),
                new SupplierResponse(
                        supplier.getFirstName(),
                        supplier.getLastName(),
                        supplier.getCedula(),
                        supplier.getPhone(),
                        supplier.getAddress(),
                        supplier.getDateRegister(),
                        supplier.getDescription()
                ),
                materials.getName(),
                materials.getType(),
                materials.getWidth(),
                materials.getHeight(),
                materials.getUnitPrice(),
                materials.getQuantity(),
                materials.getDateRegister()
        );
    }

    public static Materials mapperMaterialsUpdate(Materials materials, @Nullable Supplier supplier, MaterialsUpdate update) {

        materials.setCode(update.code());
        materials.setSupplier(supplier);
       /* materials.setSupplier(
                Objects.isNull(supplier) ?
                        materials.getSupplier() :
                        supplier
        );

        */
        materials.setName(update.name());
        materials.setType(update.type());
        materials.setWidth(update.width());
        materials.setHeight(update.height());
        materials.setUnitPrice(update.unitPrice());
        materials.setQuantity(update.quantity());

        return materials;
    }

    public static Materials mapperMaterialsDelete(Materials materials) {

        materials.setStatus(Status.INACTIVE);
        return materials;
    }
}
