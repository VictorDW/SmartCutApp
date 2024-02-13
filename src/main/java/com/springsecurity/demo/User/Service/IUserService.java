package com.springsecurity.demo.User.Service;

import com.springsecurity.demo.User.DTO.UserResponse;
import com.springsecurity.demo.User.DTO.UserUpdate;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IUserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserByUsername(String username)throws AccessDeniedException;
    UserResponse update(UserUpdate update) throws AccessDeniedException;
    void changeUserStatus(Long id);
    void isThereUsername(String username);


}
