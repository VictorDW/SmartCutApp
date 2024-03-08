package com.smartcut.app.Domain.User.Service;

import com.smartcut.app.Domain.User.DTO.UserResponse;
import com.smartcut.app.Domain.User.DTO.UserUpdate;

import java.util.List;

public interface IUserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserByUsername(String username);
    UserResponse update(UserUpdate update);
    void changeUserStatus(Long id);
    List<Object> checkUsernameAvailability(String username);
    void validateUsername(String username);
}
