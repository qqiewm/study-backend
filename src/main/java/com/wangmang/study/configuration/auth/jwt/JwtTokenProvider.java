package com.wangmang.study.configuration.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    // 키
    @Value("${auth.jwtSecret}")
    private String jwtSecret;

    // 토큰 유효시간 | 30min
    @Value("${auth.jwtExpirationMs}")
    private int jwtExpirationMs;

    //의존성 주입후 실행되는 어노테이션
    //base64 인코딩
    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    // JWT Token 생성.
    public String createToken(String user, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(user); // claims 생성 및 payload 설정
        claims.put("roles", roles); // 권한 설정, key/ value 쌍으로 저장

        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + jwtExpirationMs)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }
}