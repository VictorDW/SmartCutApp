package com.springsecurity.practica.User.Controller;

import com.springsecurity.practica.User.DTO.UserResponse;
import com.springsecurity.practica.User.DTO.UserUpdate;
import com.springsecurity.practica.User.Service.IUserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdate userUpdate) throws AccessDeniedException {
        return ResponseEntity.ok(userService.update(userUpdate));
    }
}
