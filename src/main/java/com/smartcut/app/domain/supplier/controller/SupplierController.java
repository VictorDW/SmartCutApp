package com.smartcut.app.domain.supplier.controller;

import com.smartcut.app.domain.Status;
import com.smartcut.app.domain.supplier.dto.SupplierRequest;
import com.smartcut.app.domain.supplier.dto.SupplierResponse;
import com.smartcut.app.domain.supplier.dto.SupplierUpdate;
import com.smartcut.app.domain.supplier.service.ISupplierService;
import com.smartcut.app.domain.util.ValidateStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

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
     * @return ResponseEntity con la información del proveedor creado y un código de estado HTTP 201 (Created) si la creación fue exitosa.
     */
    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@RequestBody @Valid SupplierRequest request) {

        return new ResponseEntity<>(supplierService.create(request), HttpStatus.CREATED);
    }

    /**
     * Obtiene un proveedor por su numero de cedula y devuelve una respuesta HTTP con la información del proveedor.
     *
     * @param cedula del proveedor a buscar.
     * @return ResponseEntity con la información del proveedor y un código de estado HTTP 200 (OK) si se encuentra el proveedor.
     */
    @GetMapping("/{cedula}")
    public ResponseEntity<SupplierResponse> getSupplierByCedula(@PathVariable
                                                      @Pattern(regexp = "^\\d+$", message = "{message.only.numbers}")
                                                      @Size(min = 8, max = 11, message = "{message.cedula.size}")
                                                      String cedula) {

        return ResponseEntity.ok(supplierService.getSupplierByCedula(cedula));
    }

    /**
     * Obtiene todos los proveedores y devuelve una respuesta HTTP con una lista que contiene la información de cada proveedor.
     *
     * @return ResponseEntity con una lista que contiene la información de todos los proveedores y un código de estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSupplier(@RequestParam Optional<String> status) {
        Status state = ValidateStatus.getStatus(status);
        return ResponseEntity.ok(supplierService.getAll(state));
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
     * Inactiva un proveedor utilizando su id y devuelve una respuesta HTTP sin contenido (204) si la desactivación fue exitosa.
     * @param id El id del proveedor a inactivar.
     * @return ResponseEntity sin contenido y un código de estado HTTP 204 (No Content) si la desactivación fue exitosa.
     */
    @DeleteMapping("/status/{id}")
    public ResponseEntity<HttpHeaders> changeSupplierStatus(@PathVariable @Max(value = 999, message = "{message.invalid.id}") Long id) {
        supplierService.changeSupplierStatus(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
