package com.jcayllahua.backend.service;

import com.jcayllahua.backend.model.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> findAll();
    Note findById(Long id);
    Note update(Long id, Note note);
    Note save(Note note);
    String deleteById(Long id);
    Note archiveById(Long id);
    Note unarchiveById(Long id);
    Note addCategory(Long noteId, Long categoryId);
    Note removeCategory(Long noteId, Long categoryId);
    List<Note> findByCategory(List<Long> categoriesId);
}
