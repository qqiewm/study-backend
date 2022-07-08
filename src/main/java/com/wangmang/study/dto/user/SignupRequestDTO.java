package com.wangmang.study.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequestDTO {
    private Long idx;
    private String username;
    private String email;
    private String password;
    private Set<String> role;
}
