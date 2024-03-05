package com.smartcut.app.User.Controller;

import com.smartcut.app.User.DTO.UserResponse;
import com.smartcut.app.User.DTO.UserUpdate;
import com.smartcut.app.User.Service.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final IUserService userService;

    @GetMapping("/verify/{username}")
    public ResponseEntity<String> isThereUsername(@PathVariable
                                         @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{message.special.character}")
                                         @Size(min = 4, max = 20, message = "{message.default.size}")
                                         String username) {

      var response = userService.checkUsernameAvailability(username);
      String message = (String) response.get(0);
      HttpStatus codeStatus = (HttpStatus) response.get(1);

      return new ResponseEntity<>(message,codeStatus);
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
      return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable
                                                 @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{message.special.character}")
                                                 @Size(min = 4, max = 20, message = "{message.default.size}")
                                                 String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserUpdate userUpdate) {
        return ResponseEntity.ok(userService.update(userUpdate));
    }

    @DeleteMapping("/status/{id}")
    public ResponseEntity<HttpHeaders> changeUserStatus(@PathVariable @Max(value = 999, message = "{message.invalid.id}") Long id) {
        userService.changeUserStatus(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
