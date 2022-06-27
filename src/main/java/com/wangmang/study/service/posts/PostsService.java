package com.wangmang.study.service.posts;

import com.wangmang.study.domain.posts.Posts;
import com.wangmang.study.domain.posts.PostsRepository;
import com.wangmang.study.dto.posts.PostsResponseDto;
import com.wangmang.study.dto.posts.PostsSaveRequestDto;
import com.wangmang.study.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    //게시글 등록
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //게시글 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts =  postsRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
    //전체 조회
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAll(){
        List<Posts> all = postsRepository.findAllDesc();
        List<PostsResponseDto> postsList =
                all.stream().map((post) -> new PostsResponseDto(post)).collect(Collectors.toList());
        return postsList;
    }

    //상세조회
    public PostsResponseDto findById(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDto(posts);
    }

    //게시물 삭제
    public void delete(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        postsRepository.delete(posts);
    }

}
