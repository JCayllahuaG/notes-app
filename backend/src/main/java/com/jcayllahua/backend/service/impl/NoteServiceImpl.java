package com.jcayllahua.backend.service.impl;

import com.jcayllahua.backend.model.entity.Note;
import com.jcayllahua.backend.persistence.repository.CategoryRepository;
import com.jcayllahua.backend.persistence.repository.NoteRepository;
import com.jcayllahua.backend.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private  final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public NoteServiceImpl(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public Note update(Long id, Note note) {

        var noteToUpdate = noteRepository.findById(id);

        if(noteToUpdate.isEmpty())
            throw new IllegalArgumentException("Note with id "+id+" does not exist");

        try {
            Note updatedNote = noteRepository.save(noteToUpdate.get().updateNote(note));
            return Optional.of(updatedNote).orElseThrow();
        }catch (Exception e){
            throw new IllegalArgumentException("Error while updating note:"+e.getMessage());
        }
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }


    @Override
    public String deleteById(Long id) {
        var noteToDelete = noteRepository.findById(id);

        if(noteToDelete.isEmpty())
            throw new IllegalArgumentException("Note with id "+id+" does not exist");

        try {
            noteRepository.deleteById(id);
            return "Note has been deleted";
        }catch (Exception e){
            throw new IllegalArgumentException("Error while deleting note:"+e.getMessage());
        }
    }

    @Override
    public Note archiveById(Long id) {
        var noteToArchive = noteRepository.findById(id);

        if(noteToArchive.isEmpty())
            throw new IllegalArgumentException("Note with id "+id+" does not exist");

        try {
            Note archivedNote = noteToArchive.get().archiveNote();

            return Optional.of(noteRepository.save(archivedNote)).orElseThrow();
        }catch (Exception e){
            throw new IllegalArgumentException("Error while archiving note:"+e.getMessage());
        }
    }

    @Override
    public Note unarchiveById(Long id) {
        var noteToUnarchive = noteRepository.findById(id);

        if(noteToUnarchive.isEmpty())
            throw new IllegalArgumentException("Note with id "+id+" does not exist");
        try {
            Note unarchivedNote = noteToUnarchive.get().unarchiveNote();
            return Optional.of(noteRepository.save(unarchivedNote)).orElseThrow();
        }
        catch (Exception e){
            throw new IllegalArgumentException("Error while unfiling note:"+e.getMessage());
        }
    }

    @Override
    public Note addCategory(Long noteId, Long categoryId) {
        var note = noteRepository.findById(noteId);
        if(note.isEmpty())
            throw new IllegalArgumentException("Note with id "+noteId+" does not exist");
        var category = categoryRepository.findById(categoryId);
        if(category.isEmpty())
            throw new IllegalArgumentException("Category with id "+categoryId+" does not exist");
        try {
            Note noteWithCategory = note.get();
            noteWithCategory.addCategory(category.get());
            return noteRepository.save(noteWithCategory);
        }catch (
                Exception e){
            throw new IllegalArgumentException("Error while adding category to note:"+e.getMessage());
        }

    }

    @Override
    public Note removeCategory(Long noteId, Long categoryId) {
        var note = noteRepository.findById(noteId);
        if(note.isEmpty())
            throw new IllegalArgumentException("Note with id "+noteId+" does not exist");
        var category = categoryRepository.findById(categoryId);
        if(category.isEmpty())
            throw new IllegalArgumentException("Category with id "+categoryId+" does not exist");
        try {
            Note noteWithoutCategory = note.get();
            noteWithoutCategory.removeCategory(category.get());
            return noteRepository.save(noteWithoutCategory);
        }catch (Exception e){
            throw new IllegalArgumentException("Error while removing category from note:"+e.getMessage());
        }
    }

    @Override
    public List<Note> findByCategory(List<Long> categoriesId) {

        try {
            var notesWithMatchingCategories = noteRepository.findDistinctByCategoriesIdIn(categoriesId);
            if(notesWithMatchingCategories.isEmpty())
                throw new IllegalArgumentException("No notes found with the given categories");
            return notesWithMatchingCategories.get();
        } catch (Exception e){
            throw new IllegalArgumentException("Error while finding notes by category:"+e.getMessage());
        }
    }
}
