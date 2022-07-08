package com.wangmang.study.service;

import com.wangmang.study.domain.user.User;
import com.wangmang.study.domain.user.UserRepository;
import com.wangmang.study.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long join(UserDTO user) {
        Long userId = userRepository.save(
                        User.builder()
                                .email(user.getEmail())
                                .password(passwordEncoder.encode(user.getPassword()))
                                .roles(Collections.singletonList("ROLE_USER"))
                                .build()).getId();

        return userId;
    }

    public User findUser(UserDTO user) {
        User member = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 잘못되었습니다."));
        return member;
    }

    public boolean checkPassword(User member, UserDTO user) {
        return passwordEncoder.matches(user.getPassword(), member.getPassword());
    }
}
