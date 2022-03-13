package com.salem.budgetApp.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private final int MILISECONDSINDAY = 8640000;
    private String SECRET = "MySecret";

    public String extractUserName(String token){
        var claims = extractClaims(token);
        return claims.getSubject();
    }

    public Date extractExpirationDate(String token){
        var claims = extractClaims(token);
        return claims.getExpiration();
    }

    private Claims extractClaims(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        var userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public String generateJWTToken(UserDetails userDetails){

        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userDetails.getUsername());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + MILISECONDSINDAY))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

}
