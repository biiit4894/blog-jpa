package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.dto.AddArticleRequest;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BlogControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ac;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ac).build();
    }

    @Test
    public void addArticle() throws Exception {
        // given : 저장하고 싶은 블로그 정보
        AddArticleRequest request = new AddArticleRequest("제목", "내용");
        // object -> json
        String json = objectMapper.writeValueAsString(request);

        // when : POST /api/articles
        ResultActions resultActions = mockMvc.perform(post("/api/articles")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        // then : HttpStatusCode 201인지 검증
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("content").value(request.getContent()));

        // 저장이 잘 되었는지 확인
    }

//    @Test
//    public void showArticle() throws Exception {
//        // given : blogRepository.save
//        List<Article> articleList = new ArrayList<>();
//        Article article1 = new Article("title1", "content1");
//        Article article2 = new Article("title2", "content2");
//        articleList.add(article1);
//        articleList.add(article2);
//        blogRepository.saveAll(articleList);
//
//        // when : GET /api/articles
//        ResultActions resultActions = mockMvc.perform(get("/api/articles"));
//
//        // then : 호출결과(Json)와 save한 데이터와 비교
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].title").value(articleList.get(0).getTitle()))
//                .andExpect(jsonPath("$[0].content").value(articleList.get(0).getContent()))
//                .andExpect(jsonPath("$[1].title").value(articleList.get(1).getTitle()))
//                .andExpect(jsonPath("$[1].content").value(articleList.get(1).getContent()));
//    }

    @Test
    public void deleteById() throws Exception {
        // given : 삭제할 대상 데이터 save
        Article article = blogRepository.save(new Article("title", "content"));
        Long id = article.getId();

        // when : DELETE /api/articles/{id}
        ResultActions resultActions = mockMvc.perform(delete("/api/articles/{id}", id));

        // then : 삭제 결과 확인, 200 응답 코드 확인
        resultActions.andExpect(status().isOk());

        // 삭제결과
        Optional<Article> deletedArticle = blogRepository.findById(id);
        Assertions.assertFalse(deletedArticle.isPresent());
        //Assertions.assertTrue(!deletedArticle.isPresent()); // same

    }

}