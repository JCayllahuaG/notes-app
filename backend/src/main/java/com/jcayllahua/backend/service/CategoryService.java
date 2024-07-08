package com.jcayllahua.backend.service;

import com.jcayllahua.backend.model.entity.Category;
import com.jcayllahua.backend.model.entity.Note;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    Category update(Long id, Category category);
    String delete(Long id);
}
