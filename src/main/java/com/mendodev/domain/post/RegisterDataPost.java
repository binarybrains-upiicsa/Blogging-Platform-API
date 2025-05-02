package com.mendodev.domain.post;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegisterDataPost(

        @NotBlank (message = "You MUST include a title")
        String title,

        @NotBlank (message = "You MUST include content")
        String content,

        @NotBlank (message = "You MUST include a category")
        String category,

        @NotEmpty (message = "You MUST include at least one tag")
        List<String> tags,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) {
}
