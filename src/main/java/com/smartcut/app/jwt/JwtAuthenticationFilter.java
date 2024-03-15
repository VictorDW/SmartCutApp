package com.smartcut.app.jwt;

import java.io.IOException;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final Logger loggerClass = LoggerFactory.getLogger(JwtAuthenticationFilter.class); //-> permite crear la instancia para el logger

    /**
     * Este método heredado de OncePerRequestFilter permite dar la implementación del filtro personalizado,
     * para validar el token y continuar con la cadena de filtros
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response,  
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest(request);

        if (Objects.isNull(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        updateSecurityContext(extractAuthenticatedUserFromToken(token, request));

        filterChain.doFilter(request, response);
    }

    /**
     * Este método permite verificar la validez del token, si es valido se procede a obtener el usuario autenticado,
     * de lo contrario lo que tenga el <Code>SecurityContext</Code>
     * @param token
     * @param request
     */
    private Authentication extractAuthenticatedUserFromToken(String token, HttpServletRequest request) {

        if (jwtService.isValidToken(token)) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.getUsername());

            var authenticatedUser = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

            //permite agregar más detalles de la solicitud de autenticación, como dirección ip, número de serie del certificado, etc.
            authenticatedUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            /* Ejemplo de como optener los detalles
                var details = (WebAuthenticationDetails) authenticatedUser.getDetails();
                //Mediante un logger podemos observar en consola la dirección ip
                loggerClass.info("Remote Address = "+ details.getRemoteAddress());
                 */

            return authenticatedUser;
        }

        loggerClass.warn("El SecurityContext contiene un: %s".formatted(SecurityContextHolder.getContext().getAuthentication()));
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Este método se encarga de actualizar el authentication en el SecurityContext
     * @param authenticatedUser
     */
    private void updateSecurityContext(Authentication authenticatedUser) {
        /* Se setea el usuario autenticado y autorizado en el SecurityContext,
         * en caso de que esto no succeda por la validación del token
         * el filtro AuthenticationFilter lanza una excepción que posteriormente se personaliza con el AuthenticationEntryPoint
         * definido en el ApplicationConfig */
        if (Objects.nonNull(authenticatedUser)) {
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        }
    }

    /**
     * Este método permite obtener el token en limpio, sacándolo del header de la request enviada
     * @param request
     * @return el token
     */
    private String getTokenFromRequest(HttpServletRequest request) {

        //Sacar el token del header
        final String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
           // return authHeader.substring(7);
           return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
