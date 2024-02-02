package com.springsecurity.practica.Auth;

import com.springsecurity.practica.Jwt.JwtService;
import com.springsecurity.practica.User.Role;
import com.springsecurity.practica.User.User;
import com.springsecurity.practica.User.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.practica.Jwt.AuthResponse;
import com.springsecurity.practica.Jwt.LoginRequest;
import com.springsecurity.practica.Jwt.RegisterRequest;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {

        var usernameAuthentication = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());

        Authentication userAuthenticated = authenticationManager.authenticate(usernameAuthentication);

        return AuthResponse.builder()
                .token(
                    jwtService.getToken(
                        (UserDetails) userAuthenticated.getPrincipal()
                    )
                )
                .build();
    }
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                    .username(request.getUserName())
                    .password(encodePassword(request.getPassword()))
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .country(request.getCountry())
                    .role(Role.USER)
                    .build();

        userRepository.save(user);

        return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
