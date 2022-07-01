package com.wangmang.study.controller;

import com.wangmang.study.dto.posts.PostsResponseDto;
import com.wangmang.study.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostsService postsService;


    @GetMapping("/save")
    public String save(){
        return "posts/savePost";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        model.addAttribute("post",postsService.findById(id));
        return "posts/detailPost";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("post",postsService.findById(id));
        return "posts/editPost";
    }
}
