package com.salem.budgetApp.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private final int milisecondsInDay = 8640000;
    private String secret = "MySecret";

    public String generateJWTToken(UserDetails userDetails){


        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userDetails.getUsername());


        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + milisecondsInDay))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

}
