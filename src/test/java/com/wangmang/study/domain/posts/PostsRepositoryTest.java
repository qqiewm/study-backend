package com.wangmang.study.domain.posts;

import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    void cleanup(){
        postsRepository.deleteAll();;
    }
    @Test
    void 게시글저장_조회(){
        //given
        String title = "테스트 제목";
        String content = "테스트 내용";
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("wangmang")
                .build());
        //when
        Posts posts = postsRepository.findAll().get(0);

        //then
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
    }
}