package com.wangmang.study.controller;
import com.wangmang.study.configuration.auth.jwt.JwtTokenProvider;
import com.wangmang.study.domain.user.User;
import com.wangmang.study.dto.user.UserDTO;
import com.wangmang.study.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@Log4j2
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody UserDTO user) {
        Long result = userService.join(user);
        return result;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user, HttpServletResponse response) {

        //검증
        User member = userService.findUser(user);
        boolean checkResult = userService.checkPassword(member, user);

        if(!checkResult){
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 잘못되었습니다.");
        }
        // 토큰 생성 및 응답
        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
        response.setHeader("authorization", "bearer " + token);
        return "bearer " + token;

    }
}