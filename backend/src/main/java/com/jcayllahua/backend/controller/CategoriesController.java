package com.jcayllahua.backend.controller;

import com.jcayllahua.backend.controller.resources.CategoryResource;
import com.jcayllahua.backend.controller.resources.CreateCategoryResource;
import com.jcayllahua.backend.model.entity.Category;
import com.jcayllahua.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "Category Endpoints")
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "*")
public class CategoriesController {

    private final CategoryService categoryService;


    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResource>> getCategories() {
        return ResponseEntity.ok(CategoryResource.fromEntity(categoryService.findAll()));
    }
    @PostMapping
    public ResponseEntity<CategoryResource> createCategory(CreateCategoryResource request) {
        Category category = new Category(request.name(), request.color());
        return ResponseEntity.ok(CategoryResource.fromEntity(categoryService.save(category)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResource> updateCategory(@PathVariable Long id, CreateCategoryResource categoryData) {
        Category category = new Category(categoryData.name(), categoryData.color());
        return ResponseEntity.ok(CategoryResource.fromEntity(categoryService.update(id, category)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }



}
