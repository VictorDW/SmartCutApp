package com.smartcut.app.User.Service;

import com.smartcut.app.User.DTO.UserResponse;
import com.smartcut.app.User.DTO.UserUpdate;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IUserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserByUsername(String username);
    UserResponse update(UserUpdate update);
    void changeUserStatus(Long id);
    List<Object> checkUsernameAvailability(String username);
    void validateUsername(String username);
}
