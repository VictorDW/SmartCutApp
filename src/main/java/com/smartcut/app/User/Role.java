package com.smartcut.app.User;

import com.smartcut.app.User.Permission;

import java.util.List;

public enum Role {
    ADMIN(List.of(Permission.REGISTER_ONE_USER,
                Permission.READ_ONE_USER,
                Permission.READ_ALL_USER,
                Permission.UPDATE_USER,
                Permission.CHANGE_USER_STATUS,
                Permission.DELETE_USER,
                Permission.CREATE_SUPPLIER,
                Permission.READ_ALL_SUPPLIER,
                Permission.READ_ONE_SUPPLIER,
                Permission.UPDATE_SUPPLIER,
                Permission.DELETE_SUPPLIER,
                Permission.CREATE_MATERIALS,
                Permission.READ_ALL_MATERIALS,
                Permission.READ_ONE_MATERIALS,
                Permission.UPDATE_MATERIALS,
                Permission.DELETE_MATERIALS)),
    USER(List.of(Permission.UPDATE_USER,
                Permission.READ_ONE_USER,
                Permission.READ_ALL_SUPPLIER,
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
