package com.springsecurity.practica.Domain.Supplier.Controller;

import com.springsecurity.practica.Domain.Supplier.DTO.SupplierRequest;
import com.springsecurity.practica.Domain.Supplier.DTO.SupplierResponse;
import com.springsecurity.practica.Domain.Supplier.DTO.SupplierUpdate;
import com.springsecurity.practica.Domain.Supplier.Service.ISupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que permite recibir las solicitudes a la sección de proveedores de la API y retornar las respuestas
 */
@RestController
@RequestMapping("api/supplier")
@AllArgsConstructor
@Validated
public class SupplierController {

    private final ISupplierService supplierService;

    /**
     * Crea un nuevo proveedor utilizando la información proporcionada en el cuerpo de la solicitud y devuelve una respuesta HTTP con la información del proveedor creado.
     *
     * @param request El objeto SupplierRequest que contiene la información del proveedor a crear.
     * @return ResponseEntity con la información del proveedor creado y un código de estado HTTP 200 (OK) si la creación fue exitosa.
     */
    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@RequestBody SupplierRequest request) {

        return ResponseEntity.ok(supplierService.create(request));
    }

    /**
     * Obtiene un proveedor por su ID y devuelve una respuesta HTTP con la información del proveedor.
     *
     * @param id El ID del proveedor a buscar.
     * @return ResponseEntity con la información del proveedor y un código de estado HTTP 200 (OK) si se encuentra el proveedor.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getByIdSupplier(@PathVariable @Max(value = 999, message = "ID invalido") Long id) {
        return ResponseEntity.ok(supplierService.getById(id));
    }

    /**
     * Obtiene todos los proveedores y devuelve una respuesta HTTP con una lista que contiene la información de cada proveedor.
     *
     * @return ResponseEntity con una lista que contiene la información de todos los proveedores y un código de estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSupplier() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    /**
     * Actualiza la información de un proveedor utilizando los datos proporcionados en el cuerpo de la solicitud y devuelve una respuesta HTTP con la información del proveedor actualizado.
     *
     * @param update El objeto SupplierUpdate que contiene la información actualizada del proveedor.
     * @return ResponseEntity con la información del proveedor actualizado y un código de estado HTTP 200 (OK) si la actualización fue exitosa.
     */
    @PutMapping()
    public ResponseEntity<SupplierResponse> updateSupplier(@RequestBody @Valid SupplierUpdate update) {
        return ResponseEntity.ok(supplierService.update(update));
    }

    /**
     * Inactiva un proveedor utilizando su número de cédula y devuelve una respuesta HTTP sin contenido (204) si la desactivación fue exitosa.
     *
     * @param cedula El número de cédula del proveedor a inactivar.
     * @return ResponseEntity sin contenido y un código de estado HTTP 204 (No Content) si la desactivación fue exitosa.
     */
    @PutMapping("/{cedula}")
    public ResponseEntity<HttpHeaders> deleteSupplier(@PathVariable
                                                      @Pattern(regexp = "^\\d]+$", message = "Solo debe contener solo numeros")
                                                      @Size(min = 8, max = 11, message = "Debe contener minimo 8 digitos y maximo 11")
                                                      String cedula) {

        supplierService.delete(cedula);
        return ResponseEntity.noContent().build();
    }

}
