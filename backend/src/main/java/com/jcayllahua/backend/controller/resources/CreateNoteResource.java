package com.jcayllahua.backend.controller.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateNoteResource(
        @NotBlank
                @Schema(description = "Note title", example = "My first note")
        String title,
        @NotBlank
                @Schema(description = "Note content", example = "This is the content of my first note")
        String content) {
}

