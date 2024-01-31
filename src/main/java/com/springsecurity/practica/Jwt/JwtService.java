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

@Service
public class JwtService {

    @Value("${api.security.token.secret}")//asignamos el secret desde application.properties para las buenas prácticas
    private static String SECRET_KEY;

    public String getToken(UserDetails user) {
        return getToken(extraClaims(), user);
    }

    private String getToken(Function<User, Map<String, Object>> extraClaims, UserDetails user) {
        
        //var key = Jwts.SIG.HS256.key().

        return Jwts
        .builder()
        .setClaims(extraClaims.apply((User)user))
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+10000*60*24))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
 
    }

    private Function<User,Map<String, Object>> extraClaims() {

        return user -> {
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("name", user.getFirstName());
            return extraClaims;
        };
    }

    private Key getKey() {
       
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
