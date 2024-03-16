package com.estsoft.blogjpa.model;

import com.estsoft.blogjpa.dto.ArticleResponse;
import com.estsoft.blogjpa.dto.ArticleViewResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Getter
    @Column(name = "title", nullable = false)
    private String title;

    @Getter
    @Column(name = "content", nullable = false)
    private String content;

    @Getter
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder // title, content에 대한 빌더만 생성
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article() {

    }

    public ArticleResponse toResponse() {
        return ArticleResponse.builder()
                .title(title)
                .content(content)
                .build();
    }
    public ArticleViewResponse toViewResponse() {
        return new ArticleViewResponse(id, title, content, createdAt, updatedAt);
    }

    // 교안 X
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
