package com.jcayllahua.backend.service.impl;

import com.jcayllahua.backend.model.entity.Category;
import com.jcayllahua.backend.persistence.repository.CategoryRepository;
import com.jcayllahua.backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        var existsWithName = categoryRepository.findCategoryByName(category.getName());

        if(existsWithName.isPresent()){
            throw new IllegalArgumentException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        var categoryToUpdate = categoryRepository.findById(id).orElse(null);
        if (categoryToUpdate == null) {
            throw new IllegalArgumentException("Category not found");
        }
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setColor(category.getColor());
        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public String delete(Long id) {
        var categoryToDelete = categoryRepository.findById(id).orElse(null);
        if (categoryToDelete == null) {
            throw new IllegalArgumentException("Category not found");
        }
        categoryRepository.delete(categoryToDelete);
        return "Category deleted";
    }
}
