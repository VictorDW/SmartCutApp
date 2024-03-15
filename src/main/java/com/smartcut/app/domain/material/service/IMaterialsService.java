package com.smartcut.app.domain.material.service;

import com.smartcut.app.domain.material.dto.MaterialsRequest;
import com.smartcut.app.domain.material.dto.MaterialsResponse;
import com.smartcut.app.domain.material.dto.MaterialsUpdate;
import com.smartcut.app.domain.Status;

import java.util.List;

public interface IMaterialsService{

    MaterialsResponse create(MaterialsRequest request);
    List<MaterialsResponse> getMaterialsByCode(String code);
    List<MaterialsResponse> getAll(Status status);
    MaterialsResponse update(MaterialsUpdate update);
    void changeMaterialState(Long id);
}
