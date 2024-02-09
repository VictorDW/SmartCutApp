package com.springsecurity.practica.User.Service;

import com.springsecurity.practica.User.DTO.UserResponse;

import java.util.List;

public interface IUserService {

    List<UserResponse> getAllUsers();
}
