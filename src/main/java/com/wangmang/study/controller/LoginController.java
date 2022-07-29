package com.wangmang.study.controller;

import com.wangmang.study.configuration.auth.dto.JwtResponseDTO;
import com.wangmang.study.dto.user.LoginRequestDTO;
import com.wangmang.study.dto.user.SignupRequestDTO;
import com.wangmang.study.service.LoginService;
import com.wangmang.study.service.SignupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@Log4j2
public class LoginController {

    private final SignupService signupService;
    private final LoginService loginService;

    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody LoginRequestDTO requestDTO) {
        log.info("requestDTO : {} " , requestDTO);
        return loginService.login(requestDTO);
    }

    @PostMapping("/signUp")
    public Long save(@RequestBody SignupRequestDTO signupRequestDTO)  throws InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return signupService.save(signupRequestDTO);
    }


}
