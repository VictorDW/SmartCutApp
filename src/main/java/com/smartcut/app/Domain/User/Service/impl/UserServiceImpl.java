package com.smartcut.app.Domain.User.Service.impl;

import com.smartcut.app.Error.UserNotFoundException;
import com.smartcut.app.Error.UsernameAlreadyExistException;
import com.smartcut.app.Error.WithoutPermitsException;
import com.smartcut.app.Domain.User.DTO.UserResponse;
import com.smartcut.app.Domain.User.DTO.UserUpdate;
import com.smartcut.app.Domain.User.Entity.User;
import com.smartcut.app.Domain.User.Mapper.MapperUser;
import com.smartcut.app.Domain.User.Repository.UserRepository;
import com.smartcut.app.Domain.User.Role;
import com.smartcut.app.Domain.User.Service.IUserService;
import com.smartcut.app.Util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageComponent;
    private static final String MESSAGE_USER = "Usuario";
    private static final String MESSAGE_NOTFOUND = "message.error.notfound";

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(MapperUser::mapperUserToUserResponse)
                .toList();
    }

    private void executeValidationAccess(String username){
        //Obtenemos el usuario autenticado y autorizado
        final User authenticatedUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (!(username.equals(authenticatedUser.getUsername())) && authenticatedUser.getRole().equals(Role.USER)) {
            throw new WithoutPermitsException(messageComponent.getMessage("message.error.without.permits"));
        }
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        executeValidationAccess(username);
        return userRepository.findByUsername(username)
                .map(MapperUser::mapperUserToUserResponse)
                .orElseThrow(()-> new UserNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND, MESSAGE_USER)));
    }

    public UserResponse update(UserUpdate update){

        executeValidationAccess(update.username());
        return userRepository.findById(update.id())
            .map(user -> {
                   var userUpdate = userRepository.save(MapperUser.mapperUserUpdate(user, update, passwordEncoder));
                   return MapperUser.mapperUserToUserResponse(userUpdate);
                }
            ).orElseThrow(()-> new UserNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND, MESSAGE_USER)));
    }

    @Override
    public void changeUserStatus(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                   user -> userRepository.save(MapperUser.mapperStatusUser(user)),
                   ()-> {throw new UserNotFoundException(messageComponent.getMessage(MESSAGE_NOTFOUND, MESSAGE_USER));}
                );
    }

    private boolean isUsernameAlreadyTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public List<Object> checkUsernameAvailability(String username) {

        return isUsernameAlreadyTaken(username) ?
            Arrays.asList(messageComponent.getMessage("message.warn.username"), HttpStatus.BAD_REQUEST) :
            Arrays.asList(messageComponent.getMessage("message.info.username"), HttpStatus.OK);
    }


    @Override
    public void validateUsername(String username) {
        if (isUsernameAlreadyTaken(username)) {
            throw new UsernameAlreadyExistException(messageComponent.getMessage("message.error.already.exist"));
        }
    }

}
