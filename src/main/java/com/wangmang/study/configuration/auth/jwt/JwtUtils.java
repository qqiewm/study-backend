package com.wangmang.study.configuration.auth.jwt;

import com.wangmang.study.configuration.auth.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JwtUtils {
    @Value("${auth.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
         UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

         return Jwts.builder()
                 .setSubject((userPrincipal.getUsername()))
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                 .signWith(SignatureAlgorithm.HS512, jwtSecret)
                 .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(authToken);
            return true;
        }  catch (Exception e){
            log.error(e);
        }

        return false;
    }

}
