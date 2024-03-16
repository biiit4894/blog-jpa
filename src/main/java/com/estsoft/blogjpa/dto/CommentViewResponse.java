package com.estsoft.blogjpa.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentViewResponse {
    private Long id;
    private String body;
    private LocalDateTime createdAt;

}
