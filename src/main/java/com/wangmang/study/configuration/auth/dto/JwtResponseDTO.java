package com.wangmang.study.configuration.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long idx;
    private String email;
    private String username;
    private List<String> roles;



}
