package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.dto.AddCommentRequest;
import com.estsoft.blogjpa.dto.CommentResponse;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.model.Comment;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.estsoft.blogjpa.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CommentController {
    private BlogRepository blogRepository;
    private CommentService commentService;

    public CommentController(BlogRepository blogRepository, CommentService commentService) {
        this.blogRepository = blogRepository;
        this.commentService = commentService;
    }

    @PostMapping("/api/comments/{articleId}")
    public ResponseEntity<Object> addComment(@PathVariable Long articleId, @RequestBody AddCommentRequest request) {
        Comment comment = commentService.save(articleId, request);
        CommentResponse response = new CommentResponse(comment.getBody());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
