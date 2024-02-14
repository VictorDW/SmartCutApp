package com.springsecurity.demo.Domain.Util;

import com.springsecurity.demo.Domain.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//Patrón Singleton
public class ValidateStatus {

  static ValidateStatus instance;
  static Map<String, Status> instanceStatus;

  private ValidateStatus() {
    fillMap();
  }

  private static void fillMap() {
    instanceStatus = new HashMap<>();
    instanceStatus.put("ACTIVE", Status.ACTIVE);
    instanceStatus.put("INACTIVE", Status.INACTIVE);
  }

  public Map<String, Status> getMap() {
    return instanceStatus;
  }

  public static Status getStatus(String stringStatus) {
    var status = getInstance().getMap();

     if (status.containsKey(stringStatus)) {
        return status.get(stringStatus);
     }
     return status.get("ACTIVE");
  }

  private static ValidateStatus getInstance() {
    if (Objects.isNull(instance)) {
      instance = new ValidateStatus();
    }
    return instance;
  }
}
