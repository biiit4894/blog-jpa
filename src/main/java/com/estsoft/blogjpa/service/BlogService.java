package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.dto.AddArticleRequest;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity()); // 변환한 값을 entity로 save
        // save는 blogRepository에 구현되어 있지 않는데..? hibernate CrudRepository에서 제공
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found id" + id));
        //return blogRepository.findById(id).orElse(new Article());
        // findById -> Optional로 리턴됨. 비어있으면 그냥 빈 객체 리턴
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, AddArticleRequest request) {
        // begin transaction
        Article article = findById(id);
        article.update(request.getTitle(), request.getContent());

        // commit / rollback
        return article;
    }

    @Transactional
    public Article updateTitle(Long id, AddArticleRequest request) {
        Article article = findById(id);
        blogRepository.updateTitle(id, request.getTitle());
        return article;
    }
}
