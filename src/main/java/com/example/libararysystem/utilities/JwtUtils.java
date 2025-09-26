package com.example.libararysystem.utilities;

import com.example.libararysystem.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    @Value("${app.jwtExpirationMs:900000}") // 15 minutes default
    private Long jwtExpiresIn;

    @Value("${app.jwtRefreshExpirationMs:604800000}") // 7 days default
    private Long refreshTokenExpiresIn;

    public String generateToken(User user) {
    return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("role", user.getRole())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+jwtExpiresIn))
            .signWith(jwtSecret,SignatureAlgorithm.HS512)
            .compact();
}

public String generateRefreshToken(String username) {

    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+ refreshTokenExpiresIn))
            .signWith(jwtSecret,SignatureAlgorithm.HS512)
            .compact();

}


public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(jwtSecret)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
}

public boolean validateToken(String token) {
    try
    {
        Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
    return true;
    }
    catch (Exception e)
    {
        System.err.println("Invalid JWT : "+e.getMessage());
        return false;
    }
}



}
