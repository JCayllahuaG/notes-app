package com.jcayllahua.backend.controller.resources;

import com.jcayllahua.backend.model.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

public record CategoryResource(Long id, String name, String color) {


    public static CategoryResource fromEntity(Category entity) {
        return new CategoryResource(entity.getId(), entity.getName(), entity.getColor());
    }
    public static List<CategoryResource> fromEntity(List<Category> entities) {
        return entities.stream().map(CategoryResource::fromEntity).collect(Collectors.toList());
    }
}
