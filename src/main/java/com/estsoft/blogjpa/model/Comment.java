package com.estsoft.blogjpa.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "article_id", updatable = false)
    private Long articleId;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Comment(String body) {
        this.body = body;
    }

}
