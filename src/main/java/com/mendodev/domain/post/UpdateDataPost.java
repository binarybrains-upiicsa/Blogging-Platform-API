package com.mendodev.domain.post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateDataPost(
        @NotBlank
        String title,

        @NotBlank
        String content,

        @NotBlank
        String category,

        @NotEmpty
        List<String> tags,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) {
}
