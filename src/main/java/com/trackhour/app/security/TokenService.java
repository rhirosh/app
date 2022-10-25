package com.trackhour.app.security;

import com.trackhour.app.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenService {

    private String expiration;

    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(Authentication authentication){
        User logged = (User) authentication.getPrincipal();

        Date nowTime =new Date();
        Date expirationTime = new Date(nowTime.getTime() + Long.parseLong(this.expiration));
        return Jwts.builder()
                .setIssuer("Control Api endpoint")
                .setSubject(logged.getId().toString())
                .setIssuedAt(nowTime)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }

    public Boolean isTokenIsValid(String token){
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public String getIdUserToken(String token){
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
