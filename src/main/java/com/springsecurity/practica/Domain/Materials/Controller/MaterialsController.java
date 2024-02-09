package com.springsecurity.practica.Domain.Materials.Controller;

import com.springsecurity.practica.Domain.Materials.DTO.MaterialsRequest;
import com.springsecurity.practica.Domain.Materials.DTO.MaterialsResponse;
import com.springsecurity.practica.Domain.Materials.DTO.MaterialsUpdate;
import com.springsecurity.practica.Domain.Materials.Service.IMaterialsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/materials")
@Validated
public class MaterialsController {

    private final IMaterialsService materialsService;

    /**
     * Crea nuevos materiales utilizando la información proporcionada en el cuerpo de la solicitud y devuelve una respuesta HTTP con la información de los materiales creados.
     *
     * @param materialsRequest El objeto MaterialsRequest que contiene la información de los materiales a crear.
     * @return ResponseEntity con la información de los materiales creados y un código de estado HTTP 200 (OK) si la creación fue exitosa.
     */
    @PostMapping
    public ResponseEntity<MaterialsResponse> createMaterials(@RequestBody @Valid MaterialsRequest materialsRequest) {
        return ResponseEntity.ok(materialsService.create(materialsRequest));
    }

    /**
     * Obtiene un material por su ID y devuelve una respuesta HTTP con la información del material.
     *
     * @param id El ID del material a buscar.
     * @return ResponseEntity con la información del material y un código de estado HTTP 200 (OK) si se encuentra el material.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaterialsResponse> getByIdMaterials(@PathVariable @Max(value = 999, message = "ID invalido") Long id) {
        return ResponseEntity.ok(materialsService.getById(id));
    }

    /**
     * Obtiene todos los materiales y devuelve una respuesta HTTP con una lista que contiene la información de cada material.
     *
     * @return ResponseEntity con una lista que contiene la información de todos los materiales y un código de estado HTTP 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<MaterialsResponse>> getAllMaterials() {
        return ResponseEntity.ok(materialsService.getAll());
    }

    /**
     * Actualiza la información de un material utilizando los datos proporcionados en el cuerpo de la solicitud y devuelve una respuesta HTTP con la información del material actualizado.
     *
     * @param update El objeto MaterialsUpdate que contiene la información actualizada del material.
     * @return ResponseEntity con la información del material actualizado y un código de estado HTTP 200 (OK) si la actualización fue exitosa.
     */
    @PutMapping()
    public ResponseEntity<MaterialsResponse> updateMaterials(@RequestBody @Valid MaterialsUpdate update) {
        return ResponseEntity.ok(materialsService.update(update));
    }

    /**
     * Inactiva un material utilizando su ID y devuelve una respuesta HTTP sin contenido (204) si la desactivación fue exitosa.
     *
     * @param id El ID del material a inactivar.
     * @return ResponseEntity sin contenido y un código de estado HTTP 204 (No Content) si la desactivación fue exitosa.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpHeaders> deleteMaterials(@PathVariable @Max(value = 999, message = "ID invalido") Long id) {
        materialsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
