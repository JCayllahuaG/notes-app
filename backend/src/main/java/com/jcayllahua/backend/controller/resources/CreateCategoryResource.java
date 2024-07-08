package com.jcayllahua.backend.controller.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryResource(

        @NotBlank
        String name,
        @NotBlank
        String color) {
}
