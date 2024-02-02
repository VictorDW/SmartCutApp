package com.springsecurity.practica.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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

    public String getToken(UserDetails user) {
        return getToken(extraClaims(), user);
    }

    private String getToken(Function<User, Map<String, Object>> extraClaims, UserDetails user) {

        return Jwts
        .builder()
        .setClaims(extraClaims.apply((User)user))
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(expirationDate())
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    private  Date expirationDate() {
        return new Date(System.currentTimeMillis() + (EXPIRATION_MINUTES * 60 * 1000));
    }

    private Function<User,Map<String, Object>> extraClaims() {
        return user -> {
            Map<String, Object> claims = new HashMap<>();
            claims.put("Name", user.getFirstName());
            claims.put("Rol", user.getAuthorities());
            return claims;
        };
    }

    private Key getKey() {
        // decodifica la clave secreta
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // a su vez se hashea la clave para mayor seguridad
    }
}
