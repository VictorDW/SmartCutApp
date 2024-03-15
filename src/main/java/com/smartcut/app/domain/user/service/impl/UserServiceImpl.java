package com.smartcut.app.domain.user.service.impl;

import com.smartcut.app.error.UserAlreadyExistException;
import com.smartcut.app.error.UserNotFoundException;
import com.smartcut.app.error.UsernameAlreadyExistException;
import com.smartcut.app.error.WithoutPermitsException;
import com.smartcut.app.domain.user.dto.UserResponse;
import com.smartcut.app.domain.user.dto.UserUpdate;
import com.smartcut.app.domain.user.entity.User;
import com.smartcut.app.domain.user.mapper.MapperUser;
import com.smartcut.app.domain.user.repository.UserRepository;
import com.smartcut.app.domain.user.Role;
import com.smartcut.app.domain.user.service.IUserService;
import com.smartcut.app.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;

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

    @Override
    public void existUser(String cedula) {

        var user = userRepository.findByCedula(cedula);

        if(user.isPresent()) {
            throw new UserAlreadyExistException(
                messageComponent.getMessage("message.error.cedula", MESSAGE_USER, user.get().getCedula())
            );
        }
    }

    private void existUser(String newCedula, String currentCedula) {
        if (!currentCedula.equals(newCedula)) {
            existUser(newCedula);
        }
    }

    public UserResponse update(UserUpdate update){

        executeValidationAccess(update.username());
        return userRepository.findById(update.id())
            .map(user -> {
                    existUser(update.cedula(), user.getCedula());
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
