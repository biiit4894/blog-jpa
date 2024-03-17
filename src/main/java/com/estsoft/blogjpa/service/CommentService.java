package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.dto.*;
import com.estsoft.blogjpa.exception.ArticleNotFoundException;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.model.Comment;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.estsoft.blogjpa.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final BlogRepository blogRepository;

    private final CommentRepository commentRepository;


    public CommentService(BlogRepository blogRepository, CommentRepository commentRepository) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
    }

    public Comment save(Long articleId, AddCommentRequest request) {
        Optional<Article> optionalArticle = blogRepository.findById(articleId);
        if(optionalArticle.isEmpty()) {
            throw new ArticleNotFoundException("Article not found");
        }
        Article article = optionalArticle.get();

        Comment comment = Comment.builder()
                .article(article)
                .body(request.getBody())
                .build();
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long articleId, Long commentId) {
        Optional<Article> optionalArticle = blogRepository.findById(articleId);
        if(optionalArticle.isEmpty()) {
            throw new ArticleNotFoundException("Article not found");
        }
        Article article = optionalArticle.get();

        for(Comment comment : article.getCommentList()) {
            if(comment.getId().equals(commentId)) {
                return comment;
            }
        }
        throw new RuntimeException();
    }

    public CommentListViewResponse getCommentsByArticleId(Long articleId) {
        Optional<Article> optionalArticle = blogRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            throw new ArticleNotFoundException("Article not found");
        }
        Article article = optionalArticle.get();

        // 댓글 목록을 가져와서 DTO로 변환
        List<CommentViewResponse> commentResponses = article.getCommentList().stream()
                .map(Comment::toViewResponse)
                .collect(Collectors.toList());

        // 게시글 정보와 댓글 목록을 포함한 응답 생성
        return CommentListViewResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .comments(commentResponses)
                .build();
    }
}
