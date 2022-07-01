package com.wangmang.study.controller;

import com.wangmang.study.dto.posts.PostsResponseDto;
import com.wangmang.study.dto.posts.PostsSaveRequestDto;
import com.wangmang.study.dto.posts.PostsUpdateRequestDto;
import com.wangmang.study.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostsApiController {
    private final PostsService postsService;
    //게시물 등록
    @PostMapping
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    //전체 조회
    @GetMapping
    public List<PostsResponseDto> findAll(){
        return postsService.findAll();
    }

    //게시물 조회
    @GetMapping("/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    //게시물 수정
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }
    //게시물 삭제
    @DeleteMapping("/{id}")
    public Long delete (@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

}
