package com.estsoft.blogjpa.repository;

import com.estsoft.blogjpa.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// 어떤 엔티티인지, 엔티티 식별자의 타입을 알려주기
@Repository
public interface BlogRepository extends JpaRepository<Article, Long> {

    List<Article> findByTitle(String title);
    // 단건이면 Article..여러 건이면 List<Article> ...

    void deleteByContent(String content);

    // JPQL = Java Persistence Query Language  id, title
    @Modifying
    @Query("update Article set title = :title where id = :id")
    void updateTitle(Long id, String title);

}
