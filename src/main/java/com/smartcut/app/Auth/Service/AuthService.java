package com.smartcut.app.Auth.Service;

import com.smartcut.app.Auth.DTO.LoginRequest;
import com.smartcut.app.Auth.DTO.RegisterRequest;
import com.smartcut.app.Jwt.JwtService;
import com.smartcut.app.Domain.User.Entity.User;
import com.smartcut.app.Domain.User.Mapper.MapperUser;
import com.smartcut.app.Domain.User.Repository.UserRepository;
import com.smartcut.app.Domain.User.Service.IUserService;
import com.smartcut.app.Util.TemporaryTokenStorage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartcut.app.Jwt.DTO.AuthResponse;

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
     * Este método permite autenticar un usuario registrado en la base de datos, posterior a esto almacenar el token en una lista blanca
     * @param request
     * @return un objecto AuthResponse el cual contiene el token generado con los datos del usuario autenticado + datos basico de este
     */
    public AuthResponse login(LoginRequest request) {

        /*
        * esta implementación es fundamental para la autenticación, ya que permite almacenar los datos del usuario
        * sin autenticar, para posteriormente contener el usuario ya autenticado
        */
        var userAuthenticate = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        /*
        * internamente en el método authenticate, se valida que el usuario si exista en la base de datos, luego
        * que sus credenciales sean iguales, si está correcto, se creará un UsernamePasswordAuthenticationToken
        * de nuevo por medio de su método static authenticated que retorna una instancia de sí mismo, que por medio
        * de su constructor, recibe el usuario de la base de datos, y sus permisos, a su vez al retornar esa instancia
        * como un Authentication, se puede obtener el usuario autenticado con el getPrincipal.
        */
        Authentication authenticatedUser = authenticationManager.authenticate(userAuthenticate);

        User authUser = (User) authenticatedUser.getPrincipal();

        var response = MapperUser.mapperUserAndTokenToAuthResponse(authUser, jwtService.getToken(authUser));

        //Se almacena el token creado temporalmente en una lista blanca
        TemporaryTokenStorage.addTokenToWhiteList(authUser.getUsername(), response.getToken());

        return response;
    }

    /**
     * Método que permite registrar un usuario a la base de datos
     * @param request
     */
    @Transactional
    public void register(RegisterRequest request) {

        //Se validará  si el username ya existe
        userService.validateUsername(request.getUsername());
        //Se validará  si el usuario ya esta registrado
        userService.existUser(request.getCedula());

        var user = MapperUser.mapperRegisterRequestToUser(request, passwordEncoder);
        userRepository.save(user);

    }

    /**
     * Este método permite realizar la gestión para el cierre de sessión, en el que se inhabilita el token utilizado
     * por el usuario para acceder a los endpoints
     */
    public void logout() {

        String subject = ((User) SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getPrincipal())
                                .getUsername();

        String token = TemporaryTokenStorage.getTokenOfWhiteList(subject);
        TemporaryTokenStorage.addTokenToBlackList(subject, token);
    }


}
