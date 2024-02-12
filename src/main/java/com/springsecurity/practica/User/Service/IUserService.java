package com.springsecurity.practica.User.Service;

import com.springsecurity.practica.User.DTO.UserResponse;
import com.springsecurity.practica.User.DTO.UserUpdate;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IUserService {

    List<UserResponse> getAllUsers();
    UserResponse getById(Long id) throws AccessDeniedException;
    UserResponse update(UserUpdate update) throws AccessDeniedException;
}
