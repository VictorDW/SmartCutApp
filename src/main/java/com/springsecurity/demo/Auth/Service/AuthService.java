package com.springsecurity.demo.Auth.Service;

import com.springsecurity.demo.Auth.DTO.LoginRequest;
import com.springsecurity.demo.Auth.DTO.RegisterRequest;
import com.springsecurity.demo.Jwt.JwtService;
import com.springsecurity.demo.User.Entity.User;
import com.springsecurity.demo.User.Mapper.MapperUser;
import com.springsecurity.demo.User.Repository.UserRepository;
import com.springsecurity.demo.User.Service.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.demo.Jwt.DTO.AuthResponse;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;

    /**
     * Este método permite autenticar un usuario registrado en la base de datos
     * @param request
     * @return el token generado con los datos del usuario autenticado + datos basico de este
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

       return MapperUser.mapperUserAndTokenToAuthResponse(
                    (User) userAuthenticated.getPrincipal(),
                    jwtService.getToken((UserDetails) userAuthenticated.getPrincipal())
       );
    }

    /**
     * Método que permite registrar un usuario a la base de datos
     * @param request
     */
    @Transactional
    public void register(RegisterRequest request) {

        //temporalmente, se validará en este método si el username ya existe
        userService.isThereUsername(request.getUsername());

        var user = MapperUser.mapperRegisterRequestToUser(request, passwordEncoder);
        userRepository.save(user);

    }


}
