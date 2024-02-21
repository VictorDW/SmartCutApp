package com.smartcut.app.Domain.Materials.Service;

import com.smartcut.app.Domain.Materials.DTO.MaterialsRequest;
import com.smartcut.app.Domain.Materials.DTO.MaterialsResponse;
import com.smartcut.app.Domain.Materials.DTO.MaterialsUpdate;
import com.smartcut.app.Domain.Status;

import java.util.List;

public interface IMaterialsService{

    MaterialsResponse create(MaterialsRequest request);
    List<MaterialsResponse> getMaterialsByCode(String code);
    List<MaterialsResponse> getAll(Status status);
    MaterialsResponse update(MaterialsUpdate update);
    void changeMaterialState(Long id);
}
