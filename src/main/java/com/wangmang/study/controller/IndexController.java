package com.wangmang.study.controller;

import com.wangmang.study.configuration.auth.LoginUser;
import com.wangmang.study.configuration.auth.dto.SessionUser;
import com.wangmang.study.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String posts(Model model, @LoginUser SessionUser user){
        model.addAttribute("postList",postsService.findAll());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "posts";
    }
}
