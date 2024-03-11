package com.smartcut.app.Domain.Supplier.Service.impl;

import com.smartcut.app.Domain.Status;
import com.smartcut.app.Domain.Supplier.DTO.SupplierRequest;
import com.smartcut.app.Domain.Supplier.DTO.SupplierResponse;
import com.smartcut.app.Domain.Supplier.DTO.SupplierUpdate;
import com.smartcut.app.Domain.Supplier.Entity.Supplier;
import com.smartcut.app.Domain.Supplier.Mapper.MapperSupplier;
import com.smartcut.app.Domain.Supplier.Repository.SupplierRepository;
import com.smartcut.app.Domain.Supplier.Service.ISupplierService;
import com.smartcut.app.Error.SupplierAlreadyExitsException;
import com.smartcut.app.Error.SupplierNotFoundException;
import com.smartcut.app.Util.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class SupplierServiceImpl implements ISupplierService {

    public final SupplierRepository supplierRepository;
    public final MessageUtil messageComponent;
    private static final String MESSAGE_SUPPLIER = "Proveedor";
    private static final String MESSAGE_NOTFOUND = "message.error.notfound";

    /**
     * Verifica si un proveedor ya existe y, en caso de no existir, crea uno nuevo en la base de datos.
     *
     * @param request El objeto que contiene la información del proveedor a crear.
     * @return Un objeto de tipo SupplierResponse que representa el proveedor creado o existente.
     */
    @Override
    public SupplierResponse create(SupplierRequest request) {

        existSupplier(request.cedula());

        return MapperSupplier.mapperSuppliertToSupplierResponse(
                supplierRepository.save(
                        MapperSupplier.mapperSupplierRequestToSupplier(request)
                )
        );
    }

    /**
     * Obtiene un proveedor utilizando su ID y lanza una excepción si no se encuentra, este método es usado
     * por MaterialServiceImpl para agregar ó modificar el proveedor al material.
     *
     * @param id El ID que identifica al proveedor a buscar en la base de datos.
     * @return El proveedor encontrado con el ID especificado.
     * @throws SupplierNotFoundException si no se encuentra ningún proveedor con el ID proporcionado.
     */

    @Override
    public Supplier getSupplier(Long id) {
        return supplierRepository.findBySupplierId(id)
                .filter(supplier -> supplier.getStatus() != Status.INACTIVE)
                .orElseThrow(()-> new SupplierNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND,MESSAGE_SUPPLIER)));
    }

    /**
     * Verifica si un proveedor con una cédula dada ya existe.
     * @param cedula
     * @throws SupplierAlreadyExitsException sí se encuentra un proveedor con la cédula especificada en la base de datos.
     */
    private void existSupplier(String cedula) {

        var supplier = supplierRepository.findByCedula(cedula);

        if (supplier.isPresent()) {
            throw new SupplierAlreadyExitsException(
                messageComponent.getMessage("message.error.cedula", MESSAGE_SUPPLIER, supplier.get().getCedula())
            );
        }
    }

    /**
     * Verifica si una nueva cédula es diferente de la cédula actual y, de ser así, llama al método <Code>existSupplier(String cedula)</Code>
     * @param newCedula cedula enviada en el JSON
     * @param currentCedula cedula actual del proveedor
     */
    private void existSupplier(String newCedula, String currentCedula) {
        if (!currentCedula.equals(newCedula)) {
            this.existSupplier(newCedula);
        }
    }

    /**
     * Obtiene un proveedor utilizando su cedula y devuelve un objeto de tipo SupplierResponse si se encuentra.
     *
     * @param cedula identifica al proveedor a buscar en la base de datos.
     * @return Un objeto SupplierResponse que representa el proveedor encontrado con el ID especificado.
     * @throws SupplierNotFoundException si no se encuentra ningún proveedor con el ID proporcionado.
     */
    @Override
    public SupplierResponse getSupplierByCedula(String cedula) {

        return supplierRepository.findByCedula(cedula)
            .filter(supplier -> supplier.getStatus() != Status.INACTIVE)
            .map(MapperSupplier::mapperSuppliertToSupplierResponse)
            .orElseThrow(()-> new SupplierNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND,MESSAGE_SUPPLIER)));
    }

    /**
     * Obtiene una lista que contiene representaciones de todos los proveedores almacenados en la base de datos en forma de SupplierResponse.
     *
     * @return Una lista de objetos SupplierResponse que representa todos los proveedores en la base de datos.
     */
    @Override
    public List<SupplierResponse> getAll(Status status) {
        return supplierRepository.findAllSupplier(status)
                .stream().map(MapperSupplier::mapperSuppliertToSupplierResponse)
                .toList();
    }

    /**
     * Actualiza la información de un proveedor existente utilizando los datos proporcionados en un objeto SupplierUpdate.
     *
     * @param update Un objeto SupplierUpdate que contiene la información actualizada del proveedor.
     * @return Un objeto SupplierResponse que representa el proveedor actualizado.
     * @throws SupplierNotFoundException si no se encuentra ningún proveedor con el ID proporcionado en SupplierUpdate.
     */
    @Override
    public SupplierResponse update(SupplierUpdate update) {

        return supplierRepository.findBySupplierId(update.id())
                .map(supplier -> {
                    existSupplier(update.cedula(), supplier.getCedula());
                    var supplierUpdate = supplierRepository.save(
                            MapperSupplier.mapperSupplierUpdate(supplier, update)
                    );
                    return  MapperSupplier.mapperSuppliertToSupplierResponse(supplierUpdate);
                })
                .orElseThrow(() -> new SupplierNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND,MESSAGE_SUPPLIER)));
    }

    /**
     * Pone en estado inactivo el proveedor obtenido a apartir de la cedula
     *
     * @param id El número de cédula del proveedor a descativar.
     * @throws SupplierNotFoundException si no se encuentra ningún proveedor con la cédula proporcionada.
     */
    @Override
    public void changeSupplierStatus(Long id) throws RuntimeException {
        supplierRepository.findBySupplierId(id)
                .ifPresentOrElse(
                    supplier -> supplierRepository.save(MapperSupplier.mapperStatus(supplier))
                    ,()-> {throw new SupplierNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND,MESSAGE_SUPPLIER));}
                );
    }

}
