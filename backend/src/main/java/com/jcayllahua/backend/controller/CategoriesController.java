package com.jcayllahua.backend.controller;

import com.jcayllahua.backend.controller.resources.CategoryResource;
import com.jcayllahua.backend.controller.resources.CreateCategoryResource;
import com.jcayllahua.backend.model.entity.Category;
import com.jcayllahua.backend.model.validations.ObjectValidator;
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
    private final ObjectValidator validator;


    public CategoriesController(CategoryService categoryService, ObjectValidator validator) {
        this.categoryService = categoryService;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResource>> getCategories() {
        return ResponseEntity.ok(CategoryResource.fromEntity(categoryService.findAll()));
    }
    @PostMapping
    public ResponseEntity<CategoryResource> createCategory(@RequestBody CreateCategoryResource request) {

        var validCategory =  validator.validate(request);

        Category category = new Category(validCategory.name(), validCategory.color());

        return ResponseEntity.ok(CategoryResource.fromEntity(categoryService.save(category)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResource> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryResource categoryData) {
        Category category = new Category(categoryData.name(), categoryData.color());
        return ResponseEntity.ok(CategoryResource.fromEntity(categoryService.update(id, category)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }



}
