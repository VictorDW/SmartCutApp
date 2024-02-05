package com.springsecurity.practica.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.springsecurity.practica.User.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Clase que permite manejar la creación y comprobación del token
 */
@Service
public class JwtService {

    @Value("${security.key-secret}")//asignamos el secret desde application.properties para las buenas prácticas
    private  String SECRET_KEY;

    @Value("${security.jwt.expiration-minutes}")// se asigna el tiempo de expiración de token
    private  Long EXPIRATION_MINUTES;

    //Métodos necesarios para la Autenticación
    public String getToken(UserDetails user) {
        return getToken(extraClaims((User) user), user);
    }

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

    private  Date expirationDate() {
        return new Date(System.currentTimeMillis() + (EXPIRATION_MINUTES * 60 * 1000));
    }

    private Map<String, Object> extraClaims(User user) {

        Map<String, Object> claims = new HashMap<>();
            claims.put("Name", user.getFirstName());
            claims.put("Rol", user.getAuthorities());

        return claims;
    }

    private Key getKey() {
        // decodifica la clave secreta
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // a su vez se hashea la clave para mayor seguridad
    }

    //Métodos necesarios para la Autorización

    public boolean isValidToken(String token, UserDetails userDetails) {
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpiration(token));
    }

    public String getUserNameFromToken(String token) {
       // return getClaim(token, claims -> claims.getSubject());
        return getClaim(token, Claims::getSubject);
    }

    private Claims getallClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaim(String token, Function<Claims, T> extractClaim) {
        Claims claims = getallClaims(token);
        return extractClaim.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpiration(String token) {
        return getExpiration(token).before(new Date());
    }

}
