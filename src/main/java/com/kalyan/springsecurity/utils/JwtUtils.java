package com.kalyan.springsecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component

public class JwtUtils {

    @Value("${jwt.secret.key}")
    private  String SECRET_KEY;
    //1 hr
    private final long EXPIRATION_TIME = 1000 * 60 * 60 ;

private Key getSigningKey(){
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
}
    public  String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date( System.currentTimeMillis() + EXPIRATION_TIME ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    private Claims extractAllClaimsFromToken(String token){
    return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public String extractUsername(String token){
    return extractAllClaimsFromToken(token).getSubject();

    }



    private boolean isTokenExpired(String token){
    return (extractAllClaimsFromToken(token)
            .getExpiration().before(new Date()));


    }
    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    //Now we can use Lambda Expression
    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(String token) throws Exception;
    }



}
