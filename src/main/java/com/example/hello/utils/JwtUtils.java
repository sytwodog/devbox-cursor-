package com.example.hello.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000; // 12小时

    public String generateToken(Integer id, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(key)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
