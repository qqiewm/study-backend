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

    //jwt토큰 발급
    public String generateJwtToken(Authentication authentication){
        //로그인한 사용자 정보
         UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
         log.info("user = {}", user.getUsername(), user.getPassword());
         return Jwts.builder()
                 .setSubject((user.getUsername()))
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                 //signing 알고리즘, singning key
                 .signWith(SignatureAlgorithm.HS512, jwtSecret)
                 .compact();
    }

    //발급된 token으로 username을 얻음
    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody().getSubject();
    }

    //유효한 토큰인지 검증
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
