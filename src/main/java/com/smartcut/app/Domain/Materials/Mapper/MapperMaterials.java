package com.smartcut.app.Domain.Materials.Mapper;

import com.smartcut.app.Domain.Materials.DTO.MaterialsRequest;
import com.smartcut.app.Domain.Materials.DTO.MaterialsResponse;
import com.smartcut.app.Domain.Materials.DTO.MaterialsUpdate;
import com.smartcut.app.Domain.Materials.Entity.Materials;
import com.smartcut.app.Domain.Status;
import com.smartcut.app.Domain.Supplier.DTO.SupplierResponseBasic;
import com.smartcut.app.Domain.Supplier.Entity.Supplier;
import com.smartcut.app.Util.DateUtils;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class MapperMaterials{

    private MapperMaterials(){}

    public static Materials mapperMaterialsRequestToMaterials(MaterialsRequest request, Supplier supplier) {

        return Materials
            .builder()
            .code(request.code())
            .supplier(supplier)
            .name(request.name())
            .type(request.type())
            .width(request.width())
            .height(request.height())
            .unitPrice(request.unitPrice())
            .quantity(request.quantity())
            .dateRegister(LocalDateTime.now())
            .status(Status.ACTIVE)
            .build();

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
                DateUtils.dateFormat(materials.getDateRegister())
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
