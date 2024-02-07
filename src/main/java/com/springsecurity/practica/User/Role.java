package com.springsecurity.practica.User;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN(List.of(Permission.CREATE_ONE_PERSON, Permission.READ_ALL_PERSON)),
    USER(List.of(Permission.READ_ALL_PERSON));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getAllPermissions(){
        return this.permissions;
    }
}
