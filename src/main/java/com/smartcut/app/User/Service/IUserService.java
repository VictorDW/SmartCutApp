package com.smartcut.app.User.Service;

import com.smartcut.app.User.DTO.UserResponse;
import com.smartcut.app.User.DTO.UserUpdate;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IUserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserByUsername(String username)throws AccessDeniedException;
    UserResponse update(UserUpdate update) throws AccessDeniedException;
    void changeUserStatus(Long id);
    void isThereUsername(String username);


}
