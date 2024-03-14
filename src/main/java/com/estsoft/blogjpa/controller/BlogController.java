package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.dto.AddArticleRequest;
import com.estsoft.blogjpa.dto.ArticleResponse;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BlogController {
    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/api/articles")   // json { "title": "제목", "content": "내용" }
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody AddArticleRequest request) {
        Article article = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article.toResponse());    // json { "title": "제목", "content": "내용" }
    }

    @RequestMapping(value = "/api/articles", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleResponse>> showArticle() {
        List<Article> articleList = blogService.findAll();
        List<ArticleResponse> responseList = articleList.stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    // GET /api/articles/{id}
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> showArticleById(@PathVariable Long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok(article.toResponse());
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteOneArticle(@PathVariable Long id) {
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateOneArticle(@PathVariable Long id,
                                                    @RequestBody AddArticleRequest request) {
        Article updated = blogService.update(id, request);
//        Article updated = blogService.updateTitle(id, request);
        return ResponseEntity.ok(updated);
    }

//    @PutMapping("/api/articles/{id}")
//    public ResponseEntity<Article> updateOneTitle(@PathVariable Long id, @RequestBody AddArticleRequest request) {
//        Article updated = blogService.updateTitle(id, request);
//        return ResponseEntity.ok(updated);
//    }

}
