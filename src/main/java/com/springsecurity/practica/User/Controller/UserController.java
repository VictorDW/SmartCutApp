package com.springsecurity.practica.User.Controller;

import com.springsecurity.practica.User.DTO.UserResponse;
import com.springsecurity.practica.User.DTO.UserUpdate;
import com.springsecurity.practica.User.Service.IUserService;
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

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/verify/{username}")
    public ResponseEntity<HttpHeaders> isThereUsername(@PathVariable String username) throws RuntimeException {
        userService.isThereUsername(username);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdate userUpdate) throws AccessDeniedException {
        return ResponseEntity.ok(userService.update(userUpdate));
    }
    @PutMapping("/{id}")
    public ResponseEntity<HttpHeaders> changeUserStatus(@PathVariable Long id) {
        userService.changeUserStatus(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
