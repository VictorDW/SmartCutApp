package com.smartcut.app.Domain.Materials.Mapper;

import com.smartcut.app.Domain.Materials.DTO.MaterialsRequest;
import com.smartcut.app.Domain.Materials.DTO.MaterialsResponse;
import com.smartcut.app.Domain.Materials.DTO.MaterialsUpdate;
import com.smartcut.app.Domain.Materials.Entity.Materials;
import com.smartcut.app.Domain.Status;
import com.smartcut.app.Domain.Supplier.DTO.SupplierResponseBasic;
import com.smartcut.app.Domain.Supplier.Entity.Supplier;
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
                materials.getId(),
                materials.getCode(),
                new SupplierResponseBasic(
                        supplier.getFirstName(),
                        supplier.getLastName(),
                        supplier.getPhone()
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

    public static Materials mapperState(Materials materials) {
        Status newStatusSupplier = (materials.getStatus().equals(Status.ACTIVE)) ? Status.INACTIVE : Status.ACTIVE;
        materials.setStatus(newStatusSupplier);
        return materials;
    }
}
