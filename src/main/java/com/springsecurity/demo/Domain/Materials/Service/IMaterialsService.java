package com.springsecurity.demo.Domain.Materials.Service;

import com.springsecurity.demo.Domain.Materials.DTO.MaterialsRequest;
import com.springsecurity.demo.Domain.Materials.DTO.MaterialsResponse;
import com.springsecurity.demo.Domain.Materials.DTO.MaterialsUpdate;

import java.util.List;

public interface IMaterialsService{

    MaterialsResponse create(MaterialsRequest request);
    List<MaterialsResponse> getMaterialsByCode(String code);
    List<MaterialsResponse> getAll();
    MaterialsResponse update(MaterialsUpdate update);
    void delete(Long id);
}
