package com.springsecurity.practica.User.Mapper;

import com.springsecurity.practica.Auth.DTO.RegisterRequest;
import com.springsecurity.practica.Domain.Status;
import com.springsecurity.practica.User.DTO.UserResponse;
import com.springsecurity.practica.User.DTO.UserResponseBasic;
import com.springsecurity.practica.User.Entity.User;
import com.springsecurity.practica.User.Role;

import java.time.LocalDateTime;

public class MapperUser {

    private MapperUser(){}

    public static User mapperRegisterRequestToUser(RegisterRequest request, String credencial) {

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(credencial);
        user.setEmail(request.getEmail());
        user.setCedula(request.getCedula());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setStatus(Status.ACTIVE);
        user.setDateRegister(LocalDateTime.now());
        user.setRole(request.getRole());

        return user;
    }

    public static UserResponseBasic mapperUserToUserResponseBasic(User user) {
        return new UserResponseBasic(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }

    public static UserResponse mapperUserToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getCedula(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getDateRegister(),
                user.getStatus()
        );
    }
}
