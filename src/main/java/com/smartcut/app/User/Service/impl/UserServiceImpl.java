package com.smartcut.app.User.Service.impl;

import com.smartcut.app.User.DTO.UserResponse;
import com.smartcut.app.User.DTO.UserUpdate;
import com.smartcut.app.User.Entity.User;
import com.smartcut.app.User.Mapper.MapperUser;
import com.smartcut.app.User.Repository.UserRepository;
import com.smartcut.app.User.Role;
import com.smartcut.app.User.Service.IUserService;
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
    private final String MESSAGE_USER_NOT_FOUND = "Usuario no encontrado";

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(MapperUser::mapperUserToUserResponse)
                .toList();
    }

    private void executeValidationAccess(String username) throws AccessDeniedException {
        //Obtenemos el usuario autenticado y autorizado
        final User authenticatedUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (!(username.equals(authenticatedUser.getUsername())) && authenticatedUser.getRole().equals(Role.USER)) {
            throw new AccessDeniedException("Acceso denegado!");
        }
    }

    @Override
    public UserResponse getUserByUsername(String username)throws AccessDeniedException {
        executeValidationAccess(username);
        return userRepository.findByUsername(username)
                .map(MapperUser::mapperUserToUserResponse)
                .orElseThrow(()-> new RuntimeException(MESSAGE_USER_NOT_FOUND));
    }

    public UserResponse update(UserUpdate update) throws AccessDeniedException {

        executeValidationAccess(update.username());
        return userRepository.findById(update.id())
            .map(user -> {
                   var userUpdate = userRepository.save(MapperUser.mapperUserUpdate(user, update, passwordEncoder));
                   return MapperUser.mapperUserToUserResponse(userUpdate);
                }
            ).orElseThrow(()-> new RuntimeException(MESSAGE_USER_NOT_FOUND));
    }

    @Override
    public void changeUserStatus(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                   user -> userRepository.save(MapperUser.mapperStatusUser(user)),
                   ()-> {throw new RuntimeException(MESSAGE_USER_NOT_FOUND);}
                );
    }

    @Override
    public void isThereUsername(String username) throws RuntimeException {
        var isThere = userRepository.findByUsername(username);

        if (isThere.isPresent()) {
            throw new RuntimeException("El Username se encuentra en uso");
        }

    }

}
