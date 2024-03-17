package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.dto.AddCommentRequest;
import com.estsoft.blogjpa.dto.CommentListViewResponse;
import com.estsoft.blogjpa.dto.CommentResponse;
import com.estsoft.blogjpa.dto.CommentViewResponse;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.model.Comment;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.estsoft.blogjpa.service.CommentService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long articleId, @RequestBody AddCommentRequest request) {
        Comment comment = commentService.save(articleId, request);
        CommentResponse response = new CommentResponse(comment.getBody());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("api/comments/{articleId}")
    public ResponseEntity<CommentListViewResponse> getCommentsByArticleId(@PathVariable Long articleId) {
        CommentListViewResponse response = commentService.getCommentsByArticleId(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/comments/{articleId}/{commentId}")
    public ResponseEntity<CommentViewResponse> getOneComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(articleId, commentId);
        return ResponseEntity.ok(comment.toViewResponse());
    }
}
