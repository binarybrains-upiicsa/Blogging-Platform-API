package com.mendodev.domain.post;

import java.time.LocalDateTime;
import java.util.List;

public record AnswerDataPost(
        Long id,
        String title,
        String content,
        String category,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
