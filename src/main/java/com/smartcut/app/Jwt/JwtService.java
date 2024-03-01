package com.smartcut.app.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.smartcut.app.Error.ErrorValidationTokenException;
import com.smartcut.app.Util.TemporaryTokenStorage;
import io.jsonwebtoken.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smartcut.app.User.Entity.User;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Clase que permite manejar la creación y comprobación del token
 */
@Service
public class JwtService {

    //asignamos el secret desde application.properties para las buenas prácticas
    @Value("${security.key-secret}")
    private  String SECRET_KEY;

    @Value("${security.jwt.expiration-minutes}")// se asigna el tiempo de expiración de token
    private  Long EXPIRATION_MINUTES;

    @Getter //getter solo para este atributo por medio de loombok
    private String username;
    private Claims claims;
    private final Logger loggerClass = LoggerFactory.getLogger(JwtService.class);


    //Métodos necesarios para la Autenticación

    /**
     * Este método permite ser un acceso intermedio para la creación del token,
     * permitiendo agregar los claims personalizado
     * @param user
     * @return el token
     */
    public String getToken(User user) {
        return this.getToken(this.extraClaims(user), user);
    }

    /**
     * Este método tiene la principal función de crear el token, agregando los claims personalizados
     * y el username del usuario en base de datos al token
     * @param extraClaims
     * @param user
     * @return el token
     */
    private String getToken(Map<String, ?> extraClaims, User user) {

        return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(expirationDate())
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    /**
     * Este método permite expresar la fecha en la que queremos expire el token
     * @return la fecha de expiración
     */
    private Date expirationDate() {
        return new Date(System.currentTimeMillis() + (EXPIRATION_MINUTES * 60 * 1000));
    }

    /**
     * En este método especificamos un Map que representara los claims que se quiere tenga el token
     * @param user
     * @return un Map que contiene lo claims personalizados
     */
    private Map<String, Object> extraClaims(User user) {

        Map<String, Object> claims = new HashMap<>();
            claims.put("Name", user.getFirstName());
            claims.put("Rol", user.getAuthorities());

        return claims;
    }

    /**
     * Este método permite primero, tomar la clave secretada que está codificada en Base64, y decodificarla,
     * posteriormente la hashea con el algoritmo que se especificó anteriormente
     * @return una clave secreta hasheada
     */
    private Key getKey() {
        // decodifica la clave secreta
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // a su vez se hashea la clave para mayor seguridad
    }

    //Métodos necesarios para la Autorización

    /**
     * Este método permite validar si el token está correcto, y que no se encuentre en la lista de tokens cancelados,
     * @param token
     * @return Booleano el cual permite corroborar la valides del token
     */
    public boolean isValidToken(String token) {
        this.checkTokenValidity(token);
        return !TemporaryTokenStorage.isTokenCancellation(this.getUserNameFromToken(), token);
    }

    /**
     * Este método permite obtener el username del usuario, extrayéndolo del Claim.
     * @return el username del usuario autenticado
     */
    private String getUserNameFromToken() {
        // return getClaim(token, claims -> claims.getSubject());
        username = getClaim(Claims::getSubject);
        return username;
    }

    /**
     * Este método permite obtener un claim, el cual es especificado en una lambda, a partir de suministrar la
     * instancia de Claims
     * @param extractClaim
     * @return un Tipo de Claim el cual dependerá de la lambda enviada por parámetro
     * @param <T>
     */
    private <T> T getClaim(Function<Claims, T> extractClaim) {
        return extractClaim.apply(claims);
    }

    /**
     * Este método permite revisar si el token es válido, en caso de que se capture una excepción, se registra el logger con el mensaje de error,
     * por ende si surge una captura de excepción se rompe el flujo, por otro lado, en caso de que este bien el token, se procede a guardar los Claims
     * del token
     * @param token
     */
    private void checkTokenValidity(String token) {
        try {
            claims = this.tokenAnalyzer(token).getBody();
        } catch (JwtException e) {
            loggerClass.error("TOKEN INVALIDO: %s".formatted(e.getMessage()));
            throw new ErrorValidationTokenException(e.getMessage());
        }
    }

    /**
     * Este método haciendo uso de la libreria Jwts, permite analizar y validar el token que se envia por parametro,
     * @param token
     * @return un objecto Jws<Claims> que contiene la información sobre la firma y los claims, en caso de que surga un error
     * se lanza una excepción dependiendo del error encontrado.
     */
    private Jws<Claims> tokenAnalyzer(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token);
    }
}