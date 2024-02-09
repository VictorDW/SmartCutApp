package com.springsecurity.practica.Auth.Service;

import com.springsecurity.practica.Auth.DTO.LoginRequest;
import com.springsecurity.practica.Auth.DTO.RegisterRequest;
import com.springsecurity.practica.Jwt.JwtService;
import com.springsecurity.practica.User.Mapper.MapperUser;
import com.springsecurity.practica.User.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.practica.Jwt.DTO.AuthResponse;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Este método permite autenticar un usuario registrado en la base de datos
     * @param request
     * @return el token generado con los datos del usuario autenticado
     */
    public AuthResponse login(LoginRequest request) {

        /*
        * esta implementación es fundamental para la autenticación, ya que permite almacenar los datos del usuario
        * sin autenticar, para posteriormente contener el usuario ya autenticado
        */
        var usernameAuthentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        /*
        * internamente en el método authenticate, se valida que el usuario si exista en la base de datos, luego
        * que sus credenciales sean iguales, si está correcto, se creará un UsernamePasswordAuthenticationToken
        * de nuevo por medio de su método static authenticated que retorna una instancia de sí mismo, que por medio
        * de su constructor, recibe el usuario de la base de datos, y sus permisos, a su vez al retornar esa instancia
        * como un Authentication, se puede obtener el usuario autenticado con el getPrincipal.
        */
        Authentication userAuthenticated = authenticationManager.authenticate(usernameAuthentication);

        return AuthResponse.builder()
                .token(
                    jwtService.getToken(
                        (UserDetails) userAuthenticated.getPrincipal()
                    )
                )
                .build();
    }

    /**
     * Método que permite registrar un usuario a la base de datos
     * @param request
     * @return el token generado a partir del usuario registrado
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        String credencial = encodePassword(request.getPassword());
        var user = MapperUser.mapperRegisterRequestToUser(request, credencial);
        userRepository.save(user);

        return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
    }

    /**
     * Permite codificar la contraseña pasada por el parámetro
     * @param password
     * @return la contraseña hasheada
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
