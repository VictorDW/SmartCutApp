package com.springsecurity.demo.Domain.Materials.Controller;

import com.springsecurity.demo.Domain.Materials.DTO.MaterialsRequest;
import com.springsecurity.demo.Domain.Materials.DTO.MaterialsResponse;
import com.springsecurity.demo.Domain.Materials.DTO.MaterialsUpdate;
import com.springsecurity.demo.Domain.Materials.Service.IMaterialsService;
import com.springsecurity.demo.Domain.Status;
import com.springsecurity.demo.Domain.Util.ValidateStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
     * @return ResponseEntity con la información de los materiales creados y un código de estado HTTP 201 (Created) si la creación fue exitosa.
     */
    @PostMapping
    public ResponseEntity<MaterialsResponse> createMaterials(@RequestBody @Valid MaterialsRequest materialsRequest) {
        return new ResponseEntity<>(materialsService.create(materialsRequest), HttpStatus.CREATED);
    }

    /**
     * Obtiene una serie de materiales a partir de su codigo y devuelve una respuesta HTTP.
     *
     * @param code El codigo del material a buscar.
     * @return una ResponseEntity con la información de los materiales y un código de estado HTTP 200 (OK).
     */
    @GetMapping("/{code}")
    public ResponseEntity<List<MaterialsResponse>> getByIdMaterials(@PathVariable
                                                              @Pattern(regexp = "^\\d+$", message = "Solo debe contener solo numeros")
                                                              @Size(min = 4, max = 4, message = "Debe contener 4 digitos")
                                                              String code) {
        return ResponseEntity.ok(materialsService.getMaterialsByCode(code));
    }

    /**
     * Obtiene todos los materiales y devuelve una respuesta HTTP con una lista que contiene la información de cada material.
     *
     * @return ResponseEntity con una lista que contiene la información de todos los materiales y un código de estado HTTP 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<MaterialsResponse>> getAllMaterials(@RequestParam Optional<String> status) {
        Status state = ValidateStatus.getStatus(status);
        return ResponseEntity.ok(materialsService.getAll(state));
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
    @DeleteMapping("/status/{id}")
    public ResponseEntity<HttpHeaders> changeMaterialState(@PathVariable @Max(value = 999, message = "ID invalido") Long id) {
        materialsService.changeMaterialState(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
