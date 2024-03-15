package com.smartcut.app.domain.util;

import com.smartcut.app.domain.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

//Patrón Singleton
public class ValidateStatus {

   private static ValidateStatus instance;
   private static final String STATUS_DEFAULT = "ACTIVE";
   private static Map<String, Status> instanceStatus;

  private ValidateStatus() {
    fillMap();
  }

  private void fillMap() {
    instanceStatus = new HashMap<>();
    instanceStatus.put(STATUS_DEFAULT, Status.ACTIVE);
    instanceStatus.put("INACTIVE", Status.INACTIVE);
  }

  public Map<String, Status> getMap() {
    return instanceStatus;
  }

  public static Status getStatus(Optional<String> state) {

    var status = getInstance().getMap();
    // Mapear a mayúsculas y obtener el valor, de lo contrario el estado por defecto si esta vacio
    String statusText = state.map(String::toUpperCase).orElse(STATUS_DEFAULT);

     if (status.containsKey(statusText)) {
        return status.get(statusText);
     }
     return status.get(STATUS_DEFAULT);
  }

  private static ValidateStatus getInstance() {
    if (Objects.isNull(instance)) {
      instance = new ValidateStatus();
    }
    return instance;
  }
}
