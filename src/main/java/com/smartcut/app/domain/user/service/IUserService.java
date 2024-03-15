package com.smartcut.app.domain.user.service;

import com.smartcut.app.domain.user.dto.UserResponse;
import com.smartcut.app.domain.user.dto.UserUpdate;

import java.util.List;

public interface IUserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserByUsername(String username);
    void existUser(String cedula);
    UserResponse update(UserUpdate update);
    void changeUserStatus(Long id);
    List<Object> checkUsernameAvailability(String username);
    void validateUsername(String username);
}
