package com.smartcut.app.domain.material.controller;

import com.smartcut.app.domain.material.dto.MaterialsRequest;
import com.smartcut.app.domain.material.dto.MaterialsResponse;
import com.smartcut.app.domain.material.dto.MaterialsUpdate;
import com.smartcut.app.domain.material.service.IMaterialsService;
import com.smartcut.app.domain.Status;
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
    public ResponseEntity<List<MaterialsResponse>> getMaterialsByCode(@PathVariable
                                                              @Pattern(regexp = "^\\d+$", message = "{message.only.numbers}")
                                                              @Size(min = 4, max = 4, message = "{message.code.size}")
                                                              String code) {
        return ResponseEntity.ok(materialsService.getMaterialsByCode(code));
    }

    /**
     * Obtiene todos los materiales y devuelve una respuesta HTTP con una lista que contiene la información de cada material.
     *
     * @return ResponseEntity con una lista que contiene la información de todos los materiales y un código de estado HTTP 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<MaterialsResponse>> getAllMaterials(@RequestParam(required = false) String status) {
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
    public ResponseEntity<HttpHeaders> changeMaterialState(@PathVariable @Max(value = 999, message = "{message.invalid.id}") Long id) {
        materialsService.changeMaterialState(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
