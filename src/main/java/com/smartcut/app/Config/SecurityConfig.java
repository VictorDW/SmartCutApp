package com.smartcut.app.Config;

import com.smartcut.app.Jwt.JwtAuthenticationFilter;
import com.smartcut.app.Domain.User.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase que permite configurar el manejo de los filtros de Spring Security
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AccessDeniedHandler permissionsAccessDeniedHandler;

    /**
     * Este método permite configurar la cadena de filtros de seguridad, personalizando la seguridad de Spring Security
     * deshabilitando las sesiones, ya que se manejaran las autenticaciones y autorizaciones por medio de tokens,
     * se agrega el manejador de proveedor de autenticación, el cual está configurado en la clase ApplicationConfig,
     * donde se implementan los métodos que permiten realizar la autenticación y además se configuran las rutas de
     * los endpoints que serán públicos y privadas
     *
     * @param http
     * @return la cadena de filtros
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sessionConfig ->
                sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionConfig ->
                /*
                * Como las excepciones de autenticación y autorización se da en la cadena de filtros, Se deben agregar las implementaciones
                * personalizadas para que asi sean estas las que se usen al momento de dar respuesta al cliente.
                */
                exceptionConfig.accessDeniedHandler(permissionsAccessDeniedHandler)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            /*
            * con una expresión de método de referencia podemos llamar al método,
            * es equivalente a realizar la lambda, authRequestConfig -> httpRequestPath(authRequestConfig)
            */
            .authorizeHttpRequests(this::httpRequestPath)
            .build();
    }

    /**
     * Este método permite configurar los accesos a las rutas de los endpoint con sus respectivos permisos
     * funciona de la misma manera que realizarlo en el mismo builder de http, con el método authorizeHttpRequests.
     * El fin de realizarlo en un método aparte es evitar la dificultad de lectura de configuración del securityFilterChain
     * al tener que colocar en el builder demasiadas rutas y permisos, de esta manera se hace más fácil verificar
     * que esté correcto.
     * @param authRequestConfig
     */
   private void httpRequestPath(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequestConfig) {

       final String SUPPLIER_PATH = "/api/supplier";
       final String MATERIALS_PATH = "/api/materials";
       final String USER_PATH = "/api/user";
       final String STATUS_PATCH = "/status/{id}";


       authRequestConfig.requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register").hasAuthority(Permission.REGISTER_ONE_USER.name())
                .requestMatchers(HttpMethod.GET, USER_PATH+"/verify/{username}").hasAuthority(Permission.REGISTER_ONE_USER.name())
                .requestMatchers(HttpMethod.GET, USER_PATH).hasAuthority(Permission.READ_ALL_USER.name())
                .requestMatchers(HttpMethod.GET, USER_PATH+"/{username}").hasAuthority(Permission.READ_ONE_USER.name())
                .requestMatchers(HttpMethod.PUT, USER_PATH).hasAuthority(Permission.UPDATE_USER.name())
                .requestMatchers(HttpMethod.DELETE, USER_PATH+STATUS_PATCH).hasAuthority(Permission.CHANGE_USER_STATUS.name())

                .requestMatchers(HttpMethod.POST, SUPPLIER_PATH).hasAuthority(Permission.CREATE_SUPPLIER.name())
                .requestMatchers(HttpMethod.GET, SUPPLIER_PATH).hasAuthority(Permission.READ_ALL_SUPPLIER.name())
                .requestMatchers(HttpMethod.GET, SUPPLIER_PATH+"/{cedula}").hasAuthority(Permission.READ_ONE_SUPPLIER.name())
                .requestMatchers(HttpMethod.PUT, SUPPLIER_PATH).hasAuthority(Permission.UPDATE_SUPPLIER.name())
                .requestMatchers(HttpMethod.DELETE, SUPPLIER_PATH+STATUS_PATCH).hasAuthority(Permission.DELETE_SUPPLIER.name())

                .requestMatchers(HttpMethod.POST, MATERIALS_PATH).hasAuthority(Permission.CREATE_MATERIALS.name())
                .requestMatchers(HttpMethod.GET, MATERIALS_PATH).hasAuthority(Permission.READ_ALL_MATERIALS.name())
                .requestMatchers(HttpMethod.GET, MATERIALS_PATH+"/{code}").hasAuthority(Permission.READ_ONE_MATERIALS.name())
                .requestMatchers(HttpMethod.PUT, MATERIALS_PATH).hasAuthority(Permission.UPDATE_MATERIALS.name())
                .requestMatchers(HttpMethod.DELETE, MATERIALS_PATH+STATUS_PATCH).hasAuthority(Permission.DELETE_MATERIALS.name())
                .anyRequest().authenticated();
    }
}
