package com.jcayllahua.backend.controller.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateNoteResource(
        @NotBlank
        String title,
        @NotBlank
        String content) {
}

