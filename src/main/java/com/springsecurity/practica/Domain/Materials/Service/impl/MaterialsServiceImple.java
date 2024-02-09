package com.springsecurity.practica.Domain.Materials.Service.impl;

import com.springsecurity.practica.Domain.Materials.DTO.MaterialsRequest;
import com.springsecurity.practica.Domain.Materials.DTO.MaterialsResponse;
import com.springsecurity.practica.Domain.Materials.DTO.MaterialsUpdate;
import com.springsecurity.practica.Domain.Materials.Mapper.MapperMaterials;
import com.springsecurity.practica.Domain.Materials.Repository.MaterialsRepository;
import com.springsecurity.practica.Domain.Materials.Service.IMaterialsService;
import com.springsecurity.practica.Domain.Status;
import com.springsecurity.practica.Domain.Supplier.Service.ISupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class MaterialsServiceImple implements IMaterialsService {

    private final ISupplierService supplierService;
    private final MaterialsRepository materialsRepository;

    /**
     * Crea un nuevo material y lo asocia a un proveedor existente utilizando la información proporcionada.
     *
     * @param request El objeto MaterialsRequest que contiene la información del material a crear y el ID del proveedor.
     * @return Un objeto MaterialsResponse que representa el material creado.
     */
    @Override
    public MaterialsResponse create(MaterialsRequest request) {

        var supplier = supplierService.getSupplierById(request.IDSupplier());

        return MapperMaterials.mapperMaterialsToMaterialsResponse(
                materialsRepository.save(
                        MapperMaterials.mapperMaterialsRequestToMaterials(request, supplier)
                )
        );
    }

    /**
     * Obtiene un material utilizando su ID y devuelve un objeto MaterialsResponse si se encuentra.
     *
     * @param id El ID que identifica al material a buscar en la base de datos.
     * @return Un objeto MaterialsResponse que representa el material encontrado con el ID especificado.
     * @throws RuntimeException si no se encuentra ningún material con el ID proporcionado.
     */
    @Override
    public MaterialsResponse getById(Long id) {

        return materialsRepository.findByIdAndStatusNot(id, Status.INACTIVE)
                .map(MapperMaterials::mapperMaterialsToMaterialsResponse)
                .orElseThrow(()-> new RuntimeException("Material no encontrado"));
    }

    /**
     * Obtiene una lista que contiene representaciones de todos los materiales almacenados en la base de datos en forma de MaterialsResponse.
     *
     * @return Una lista de objetos MaterialsResponse que representa todos los materiales en la base de datos.
     */
    @Override
    public List<MaterialsResponse> getAll() {

        return materialsRepository.findAllByStatusNot(Status.INACTIVE).stream()
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

        var supplier = supplierService.getSupplierById(update.IDSupplier());

        return materialsRepository.findByIdAndStatusNot(update.id(), Status.INACTIVE)
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

                }).orElseThrow(()-> new RuntimeException("Material no encontrado"));
    }


    /**
     * Pone en esta inactivo un material de la base de datos utilizando su ID.
     *
     * @param id El ID que identifica al material a inactivar.
     * @throws RuntimeException si no se encuentra ningún material con el ID proporcionado.
     */
    @Override
    public void delete(Long id)  throws RuntimeException {

        materialsRepository.findByIdAndStatusNot(id, Status.INACTIVE)
          .ifPresentOrElse(
                materials ->
                  materialsRepository.save(MapperMaterials.mapperMaterialsDelete(materials))
                ,()-> {throw new RuntimeException("Material no encontrado");}
          );
    }
}
