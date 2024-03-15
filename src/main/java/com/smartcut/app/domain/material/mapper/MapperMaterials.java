package com.smartcut.app.domain.material.mapper;

import com.smartcut.app.domain.material.dto.MaterialsRequest;
import com.smartcut.app.domain.material.dto.MaterialsResponse;
import com.smartcut.app.domain.material.dto.MaterialsUpdate;
import com.smartcut.app.domain.material.entity.Materials;
import com.smartcut.app.domain.Status;
import com.smartcut.app.domain.supplier.dto.SupplierResponseBasic;
import com.smartcut.app.domain.supplier.entity.Supplier;
import com.smartcut.app.util.DateUtils;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
            .unitPrice(formattedPrice(request.unitPrice()))
            .quantity(request.quantity())
            .dateRegister(LocalDateTime.now())
            .status(Status.ACTIVE)
            .build();

    }

    public static MaterialsResponse mapperMaterialsToMaterialsResponse(Materials materials) {

        var supplier = materials.getSupplier();
        DecimalFormat formatCOP = new DecimalFormat("#,##0.00");

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
            formatCOP.format(materials.getUnitPrice()),
            materials.getQuantity(),
            DateUtils.dateFormat(materials.getDateRegister())
        );
    }

    public static Materials mapperMaterialsUpdate(Materials materials, @Nullable Supplier supplier, MaterialsUpdate update) {

        materials.setCode(update.code());
        materials.setSupplier(supplier);
        materials.setName(update.name());
        materials.setType(update.type());
        materials.setWidth(update.width());
        materials.setHeight(update.height());
        materials.setUnitPrice(formattedPrice(update.unitPrice()));
        materials.setQuantity(update.quantity());

        return materials;
    }

    public static Materials mapperState(Materials materials) {
        Status newStatusSupplier = (materials.getStatus().equals(Status.ACTIVE)) ? Status.INACTIVE : Status.ACTIVE;
        materials.setStatus(newStatusSupplier);
        return materials;
    }

    private static BigDecimal formattedPrice(BigDecimal price) {
        String convertPrice = price.toString().replace(".", "");
        return new BigDecimal(convertPrice);
    }
}
