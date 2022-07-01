package com.wangmang.study.configuration.auth.dto;

import com.wangmang.study.domain.user.User;
import groovy.transform.ToString;
import lombok.Getter;

import java.io.Serializable;

@ToString
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
