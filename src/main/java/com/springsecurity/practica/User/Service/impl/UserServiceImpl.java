package com.springsecurity.practica.User.Service.impl;

import com.springsecurity.practica.User.DTO.UserResponse;
import com.springsecurity.practica.User.Entity.User;
import com.springsecurity.practica.User.Mapper.MapperUser;
import com.springsecurity.practica.User.Repository.UserRepository;
import com.springsecurity.practica.User.Role;
import com.springsecurity.practica.User.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    public static User authenticatedUser;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(MapperUser::mapperUserToUserResponse)
                .toList();
    }

    @Override
    public UserResponse getById(Long id) throws AccessDeniedException {

        if (!id.equals(authenticatedUser.getId()) && authenticatedUser.getRole().equals(Role.USER)) {
            throw new AccessDeniedException("Access Denied!");
        }
        return userRepository.findById(id)
           /* .filter(
                user -> user.getId().equals(authenticatedUser.getId()) || authenticatedUser.getRole().equals(Role.ADMIN)) */
            .map(MapperUser::mapperUserToUserResponse)
            .orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
    }


}
