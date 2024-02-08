package com.springsecurity.practica.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.springsecurity.practica.errores.ErrorValidationTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.springsecurity.practica.User.Entity.User;

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

    //Métodos necesarios para la Autenticación

    /**
     * Este método permite ser un acceso intermedio para la creación del token,
     * permitiendo agregar los claims personalizado
     * @param user
     * @return el token
     */
    public String getToken(UserDetails user) {
        return getToken(extraClaims((User) user), user);
    }

    /**
     * Este método tiene la principal función de crear el token, agregando los claims personalizados
     * y el username del usuario en base de datos al token
     * @param extraClaims
     * @param user
     * @return el token
     */
    private String getToken(Map<String, ?> extraClaims, UserDetails user) {

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
    private  Date expirationDate() {
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
     * Este método permite validar si el token está correcto, tanto en los datos del usuario autenticado,
     * asi como la expiración de este
     * @param token
     * @param userDetails
     * @return Booleano el cual permite corroborar la valides del token
     */
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpiration(token));
    }

    /**
     * Este método permite obtener el username del usuario, extrayéndolo del token.
     * @param token
     * @return el username del usuario autenticado
     */
    public String getUserNameFromToken(String token) {
       // return getClaim(token, claims -> claims.getSubject());
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Este método permite obtener los Claims, después de la validación de la correcta estructura del token enviado.
     * @param token
     * @return la instancia de Claims, el cual contiene todos los agregados en la creación del token
     */
    private Claims getallClaims(String token)  {
        try  {
            return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        }catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            //Se capturan las excepciones propies de Jwts y se crea una personalizada al momento que se lanzan
            throw new ErrorValidationTokenException("TOKEN INVALIDO O EXPIRADO!");
        }

    }

    /**
     * Este método permite obtener un claim, el cual es especificado en una lambda, a partir de suministrar la
     * instancia del Claims
     * @param token
     * @param extractClaim
     * @return un Tipo de Claim el cual dependerá de la lambda enviada por parámetro
     * @param <T>
     */
    private <T> T getClaim(String token, Function<Claims, T> extractClaim) {
        Claims claims = getallClaims(token);
        return extractClaim.apply(claims);
    }

    /**
     * Este método permite obtener la fecha de expiración, extraída desde el token
     * @param token
     * @return la fecha de expiración del token
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Este método permite validar si la fecha de expiración del token, es menor a la fecha actual
     * @param token
     * @return un Booleano que confirma si el token ya expiro
     */
    private Boolean isTokenExpiration(String token) {
        return getExpiration(token).before(new Date());
    }

}