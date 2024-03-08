package com.smartcut.app.Domain.Materials.Service.impl;

import com.smartcut.app.Domain.Materials.DTO.MaterialsRequest;
import com.smartcut.app.Domain.Materials.DTO.MaterialsResponse;
import com.smartcut.app.Domain.Materials.DTO.MaterialsUpdate;
import com.smartcut.app.Domain.Materials.Mapper.MapperMaterials;
import com.smartcut.app.Domain.Materials.Repository.MaterialsRepository;
import com.smartcut.app.Domain.Materials.Service.IMaterialsService;
import com.smartcut.app.Domain.Status;
import com.smartcut.app.Domain.Supplier.Service.ISupplierService;
import com.smartcut.app.Error.MaterialNotFoundException;
import com.smartcut.app.Util.MessageComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class MaterialsServiceImple implements IMaterialsService {

    private final ISupplierService supplierService;
    private final MaterialsRepository materialsRepository;
    private final MessageComponent messageComponent;
    private static final  String MESSAGE_MATERIALS = "Material";
    private static final String MESSAGE_NOTFOUND = "message.error.notfound";

    /**
     * Crea un nuevo material y lo asocia a un proveedor existente utilizando la información proporcionada.
     *
     * @param request El objeto MaterialsRequest que contiene la información del material a crear y el ID del proveedor.
     * @return Un objeto MaterialsResponse que representa el material creado.
     */
    @Override
    public MaterialsResponse create(MaterialsRequest request) {

        var supplier = supplierService.getSupplier(request.IDSupplier());

        return MapperMaterials.mapperMaterialsToMaterialsResponse(
                materialsRepository.save(
                        MapperMaterials.mapperMaterialsRequestToMaterials(request, supplier)
                )
        );
    }

    /**
     * Obtiene uno o varios matereales por su codigo y devuelve una lista de MaterialsResponse si se encuentra.
     *
     * @param code identifica a los materiales a buscar en la base de datos.
     * @return Una lista de MaterialsResponse que representa los materiales encontrados con el mismo codigo o una lista vacia.
     */
    @Override
    public List<MaterialsResponse> getMaterialsByCode(String code) {

        return materialsRepository.findMaterialsByCode(code)
            .map(materials ->
                materials.stream()
                    .map(MapperMaterials::mapperMaterialsToMaterialsResponse)
                    .toList())
            .orElse(new ArrayList<>());

    }

    /**
     * Obtiene una lista que contiene representaciones de todos los materiales almacenados en la base de datos en forma de MaterialsResponse.
     *
     * @return Una lista de objetos MaterialsResponse que representa todos los materiales en la base de datos.
     */
    @Override
    public List<MaterialsResponse> getAll(Status status) {

        return materialsRepository.findAllMaterials(status).stream()
                .map(MapperMaterials::mapperMaterialsToMaterialsResponse)
                .toList();
    }

    /**
     * Actualiza la información de un material existente utilizando los datos proporcionados en un objeto MaterialsUpdate.
     *
     * @param update Un objeto MaterialsUpdate que contiene la información actualizada del material.
     * @return Un objeto MaterialsResponse que representa el material actualizado.
     * @throws RuntimeException si no se encuentra ningún material con el ID proporcionado en MaterialsUpdate.
     */
    @Override
    public MaterialsResponse update(MaterialsUpdate update) {

        var supplier = supplierService.getSupplier(update.IDSupplier());

        return materialsRepository.findMaterialById(update.id())
                .map(material -> {
                    var materialUpdate = materialsRepository.save(
                                MapperMaterials.mapperMaterialsUpdate(
                                        material,
                                        material.getSupplier().getId().equals(supplier.getId()) ?
                                                material.getSupplier():
                                                supplier
                                        ,update
                                )
                    );
                    return MapperMaterials.mapperMaterialsToMaterialsResponse(materialUpdate);

                }).orElseThrow(() -> new MaterialNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND, MESSAGE_MATERIALS)));
    }


    /**
     * Pone en esta inactivo un material de la base de datos utilizando su ID.
     *
     * @param id El ID que identifica al material a inactivar.
     * @throws RuntimeException si no se encuentra ningún material con el ID proporcionado.
     */
    @Override
    public void changeMaterialState(Long id)  throws RuntimeException {

        materialsRepository.findMaterialById(id)
          .ifPresentOrElse(
                materials ->
                  materialsRepository.save(MapperMaterials.mapperState(materials))
                ,()-> {throw new MaterialNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND, MESSAGE_MATERIALS));}
          );
    }
}
