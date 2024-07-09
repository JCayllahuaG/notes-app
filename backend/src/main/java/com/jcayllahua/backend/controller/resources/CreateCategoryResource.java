package com.jcayllahua.backend.controller.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateCategoryResource(

        @NotBlank
        @Schema(description = "Category name", example = "Personal")
        String name,
        @Schema(description = "Category color", example = "#FF0000")
        @NotBlank
        String color) {
}
