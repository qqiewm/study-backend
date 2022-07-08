package com.wangmang.study.service;

import com.wangmang.study.configuration.auth.UserDetailsImpl;
import com.wangmang.study.configuration.auth.dto.JwtResponseDTO;
import com.wangmang.study.configuration.auth.jwt.JwtUtils;
import com.wangmang.study.dto.user.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoginService {


    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    // 로그인
    public JwtResponseDTO login(LoginRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        log.info("jwt token === {}", jwt);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        JwtResponseDTO jwtDto = JwtResponseDTO.builder()
                .token(jwt)
                .idx(userDetails.getIdx())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .build();


        return jwtDto;

    }
}
