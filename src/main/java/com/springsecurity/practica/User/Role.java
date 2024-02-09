package com.springsecurity.practica.User;

import java.util.List;

public enum Role {
    ADMIN(List.of(Permission.CREATE_SUPPLIER,
                Permission.READ_ALL_SUPPLIER,
                Permission.READ_ONE_SUPPLIER,
                Permission.UPDATE_SUPPLIER,
                Permission.DELETE_SUPPLIER,
                Permission.CREATE_MATERIALS,
                Permission.READ_ALL_MATERIALS,
                Permission.READ_ONE_MATERIALS,
                Permission.UPDATE_MATERIALS,
                Permission.DELETE_MATERIALS)),
    USER(List.of(Permission.READ_ALL_SUPPLIER,
                Permission.READ_ONE_SUPPLIER,
                Permission.READ_ALL_MATERIALS,
                Permission.READ_ONE_MATERIALS,
                Permission.CREATE_SUPPLIER,
                Permission.CREATE_MATERIALS));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getAllPermissions(){
        return this.permissions;
    }
}
