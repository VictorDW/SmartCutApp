package com.springsecurity.practica.User.Service.impl;

import com.springsecurity.practica.User.DTO.UserResponse;
import com.springsecurity.practica.User.DTO.UserUpdate;
import com.springsecurity.practica.User.Entity.User;
import com.springsecurity.practica.User.Mapper.MapperUser;
import com.springsecurity.practica.User.Repository.UserRepository;
import com.springsecurity.practica.User.Role;
import com.springsecurity.practica.User.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(MapperUser::mapperUserToUserResponse)
                .toList();
    }


    private void executeValidationAccess(Long id) throws AccessDeniedException {
        //Obtenemos el usuario autenticado y autorizado
        final User authenticatedUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (!id.equals(authenticatedUser.getId()) && authenticatedUser.getRole().equals(Role.USER)) {
            throw new AccessDeniedException("ACCESS DENIED!");
        }
    }

    @Override
    public UserResponse getById(Long id) throws AccessDeniedException {

        executeValidationAccess(id);
        return userRepository.findById(id)
           /* .filter(
                user -> user.getId().equals(authenticatedUser.getId()) || authenticatedUser.getRole().equals(Role.ADMIN)) */
            .map(MapperUser::mapperUserToUserResponse)
            .orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
    }

    public UserResponse update(UserUpdate update) throws AccessDeniedException {

        executeValidationAccess(update.id());
        return userRepository.findById(update.id())
            .map(user -> {
                    var userUpdate = userRepository.save(MapperUser.mapperUserUpdate(user, update, passwordEncoder));

                    return MapperUser.mapperUserToUserResponse(userUpdate);
                }
            ).orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
    }
}
