package com.springsecurity.practica.config;

import com.springsecurity.practica.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

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
}
