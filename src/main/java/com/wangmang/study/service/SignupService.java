package com.wangmang.study.service;


import com.wangmang.study.domain.user.User;
import com.wangmang.study.domain.user.UserRepository;
import com.wangmang.study.dto.user.SignupRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Service
@Log4j2
public class SignupService {
    private final UserRepository userRepository;
    @Qualifier("passwordEncoder")
    private final PasswordEncoder encoder;

    @Value("${auth.jwtSecret}")
    private String jwtSecret;

    @Transactional
    public Long save(SignupRequestDTO signupRequestDTO) {

        User user = User.builder()
                .username(signupRequestDTO.getUsername())
                .password(encoder.encode(signupRequestDTO.getPassword()))
                .email(signupRequestDTO.getEmail())
                .build();

        return userRepository.save(user).getIdx();


    }
}
