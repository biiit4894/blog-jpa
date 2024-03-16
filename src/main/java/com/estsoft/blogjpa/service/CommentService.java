package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.dto.AddArticleRequest;
import com.estsoft.blogjpa.dto.AddCommentRequest;
import com.estsoft.blogjpa.exception.ArticleNotFoundException;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.model.Comment;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.estsoft.blogjpa.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
