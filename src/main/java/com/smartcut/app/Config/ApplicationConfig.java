package com.smartcut.app.Config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcut.app.Error.ErrorResponse;
import com.smartcut.app.Error.ExceptionManager;
import com.smartcut.app.Domain.User.Repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final Logger loggerClass = LoggerFactory.getLogger(ApplicationConfig.class);

    /**
     * Este método permite crear la instancia de <code>AuthenticationManager</code> para ser usado en el contexto de Spring boot
     * el cual es importante para realizar la autenticación del usuario
     * @param authenticationConfiguration
     * @return la instancia de <code>AuthenticationManager</code>
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Este método permite configurar el proveedor de autenticación, se le pasa el servicio que permite obtener el <code>UserDatails</code>
     * y ademas la instancia de la clase que se utilizara para comprobar la credenciales
     * @return la instancia de <code>AuthenticationProvider</code>
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    /**
     * Permite retornar la implementación de preferencia, que se usara para comprobar y hashear las credenciales
     * @return la implementación <code>BCryptPasswordEncoder</code>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }

    /**
     * Este método permite crear una clase anonima de tipo <code>UserDetailsService</code>,
     * en el que se le define el comportamiento que tendra su método <code>loadUserByUsername</code>
     * para retorne la representación del usuario en base de datos, como tipo <code>UserDetails</code>
     * @return una lambda que contiene el comportamiento para obtener el <code>UserDetails</code>
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not fount"));
    }

    /**
     * Este método permite crear una clase anonima de AuthenticationEntryPoint,
     * la cual es responsable de devolver una respuesta HTTP que indica que se requiere autenticación
     * a partir de un token válido,
     *
     * @return una lambada que contiene el comportamiento del método de la clase anonima,
     * que se llama cuando un usuario intenta acceder a un recurso protegido sin autenticación,
     * modificando la respuesta del httpServletResponse, por la personalizada.
     */
    @Bean
    public AuthenticationEntryPoint jwtAuthenticationEntrypoint() {
        return (request, response, authException) -> {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(this.convertExceptionToJson(
               "Autenticación Fallida: TOKEN INVALIDO O EXPIRADO",
               HttpStatus.UNAUTHORIZED
            ));
        };
    }

    /**
     * Este método permite crear una clase anonima de AccessDeniedHandler,
     * el cual es responsable de devolver una respuesta HTTP que indica que el usuario no tiene permisos
     * para acceder al recurso solicitado.
     *
     * @return una lambada que contiene el comportamiento del método de la clase anonima,
     * que se utiliza para manejar los casos en los que un usuario autenticado
     * intenta acceder a un recurso para el cual no tiene permisos,
     * modificando la respuesta del httpServletResponse, por la personalizada.
     */
    @Bean
    public AccessDeniedHandler permissionsAccessDeniedHandler() {
        return (request, response, accessDeniedException) ->  {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(convertExceptionToJson(
              "Solicitud Denegada: NO TIENE LOS PERMISOS",
              HttpStatus.FORBIDDEN
            ));
        };
    }

    /**
     * Este método permite convertir un objecto de ErrorResponse a un String con la estructura de un JSON
     * @param message
     * @param status
     * @return un String que contiene la información agregada al ErrorResponse
     */
    private String convertExceptionToJson(String message, HttpStatus status) {

        ErrorResponse responseBody = ExceptionManager.generalExceptionHandler(message, status).getBody();
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(responseBody);
        }catch (JsonProcessingException e) {
            loggerClass.error(e.getMessage());
            return "";
        }
    }
}
