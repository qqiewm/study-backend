package com.wangmang.study.dto.user;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private Long idx;
    private String username;
    private String password;

}