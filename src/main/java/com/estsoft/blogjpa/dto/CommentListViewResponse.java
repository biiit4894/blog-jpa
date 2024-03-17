package com.estsoft.blogjpa.dto;

import com.estsoft.blogjpa.model.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentListViewResponse {
    private Long articleId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentViewResponse> comments;
}
