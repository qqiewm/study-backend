package com.wangmang.study.controller;

import com.wangmang.study.domain.posts.Posts;
import com.wangmang.study.domain.posts.PostsRepository;
import com.wangmang.study.dto.posts.PostsSaveRequestDto;
import com.wangmang.study.dto.posts.PostsUpdateRequestDto;
import com.wangmang.study.service.PostsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private PostsService postsService;
    @AfterEach
    public void tearDown()throws Exception {
        postsRepository.deleteAll();
    }
    
    @Test
    void 서비스_전체조회() {
        postsService.findAll().stream().forEach((post) -> System.out.println("post.getTitle() = " + post.getTitle()));
    }
    @Test
    public void 게시물등록() throws Exception{
        //given
        String title = "test title";
        String content = "test content";


        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto ,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void 게시물수정() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        String expectedTitle = "title2";
        String expectedContent = "content2";

        Long updatedId = savedPosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedId;

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();


        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

    @Test
    public void JpaAuditingTest(){
        LocalDateTime now = LocalDateTime.of(2022,6,16,20,0,0);
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Posts posts = postsRepository.findAll().get(0);

        System.out.println("CreatedDate() = " + posts.getCreatedDate() + " modifiedDate" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
        
    }
}