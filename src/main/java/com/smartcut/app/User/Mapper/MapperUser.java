package com.smartcut.app.User.Mapper;

import com.smartcut.app.Auth.DTO.RegisterRequest;
import com.smartcut.app.Domain.Status;
import com.smartcut.app.Jwt.DTO.AuthResponse;
import com.smartcut.app.User.DTO.UserResponse;
import com.smartcut.app.User.DTO.UserUpdate;
import com.smartcut.app.User.Entity.User;
import com.smartcut.app.Util.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class MapperUser {


    private MapperUser(){}


    public static User mapperRegisterRequestToUser(RegisterRequest request, PasswordEncoder passwordEncoder) {

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setCedula(request.getCedula());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setStatus(Status.ACTIVE);
        user.setDateRegister(LocalDateTime.now());
        user.setRole(request.getRole());

        return user;
    }

    public static AuthResponse mapperUserAndTokenToAuthResponse(User user, String token) {
        return AuthResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .role(user.getRole())
            .token(token)
            .build();
    }

    public static UserResponse mapperUserToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getCedula(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                DateUtils.dateFormat(user.getDateRegister()),
                user.getStatus()
        );
    }

    public static User mapperUserUpdate(User user, UserUpdate update, PasswordEncoder passwordEncoder) {

        user.setPassword(update.username());
        user.setPassword(passwordEncoder.encode(update.newPassword()));
        user.setFirstName(update.firstName());
        user.setLastName(update.lastName());
        user.setEmail(update.email());
        user.setCedula(update.cedula());
        user.setPhone(update.phone());
        user.setAddress(update.address());

        return user;
    }

    public static User mapperStatusUser(User user) {
        Status newStatusUser = (user.getStatus().equals(Status.ACTIVE)) ? Status.INACTIVE : Status.ACTIVE;
        user.setStatus(newStatusUser);
        return user;
    }
}
