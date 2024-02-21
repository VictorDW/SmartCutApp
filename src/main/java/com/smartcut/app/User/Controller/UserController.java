package com.smartcut.app.User.Controller;

import com.smartcut.app.User.DTO.UserResponse;
import com.smartcut.app.User.DTO.UserUpdate;
import com.smartcut.app.User.Service.IUserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;

    @GetMapping("/verify/{username}")
    public ResponseEntity<HttpHeaders> isThereUsername(@PathVariable
                                                       @Pattern(regexp = "^[A-Za-z0-9]+$", message = "No debe contener caracteres especiales")
                                                       @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
                                                       String username) throws RuntimeException {
      userService.isThereUsername(username);
      return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
      return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable
                                                            @Pattern(regexp = "^[A-Za-z0-9]+$", message = "No debe contener caracteres especiales")
                                                            @Size(min = 4, max = 20, message = "Debe contener minimo 4 caracteres y maximo 20")
                                                            String username) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdate userUpdate) throws AccessDeniedException {
        return ResponseEntity.ok(userService.update(userUpdate));
    }

    @DeleteMapping("/status/{id}")
    public ResponseEntity<HttpHeaders> changeUserStatus(@PathVariable @Max(value = 999, message = "ID invalido") Long id) {
        userService.changeUserStatus(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
