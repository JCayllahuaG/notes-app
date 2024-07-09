package com.jcayllahua.backend.controller;

import com.jcayllahua.backend.controller.resources.CreateNoteResource;
import com.jcayllahua.backend.controller.resources.NoteResource;
import com.jcayllahua.backend.controller.resources.UpdateNoteResource;
import com.jcayllahua.backend.model.entity.Note;
import com.jcayllahua.backend.model.validations.ObjectValidator;
import com.jcayllahua.backend.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@Tag(name = "Note Endpoints")
@CrossOrigin(origins = "*")
public class NotesController {

    private final NoteService noteService;
    private final ObjectValidator validator;

    public NotesController(NoteService noteService, ObjectValidator validator) {
        this.noteService = noteService;
        this.validator = validator;
    }

    @Operation(summary = "Get all notes")
    @GetMapping
    public ResponseEntity<List<NoteResource>> getNotes() {
        return ResponseEntity.ok(NoteResource.fromEntity(noteService.findAll()));
    }

    @Operation(summary = "Get note by id")
    @GetMapping("/{id}")
    public ResponseEntity<NoteResource> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(NoteResource.fromEntity(noteService.findById(id)));
    }

    @Operation(summary = "Create a new note")
    @PostMapping
    public ResponseEntity<NoteResource> createNote(@RequestBody CreateNoteResource note) {

        var validNote = validator.validate(note);

        var noteEntity = new Note(validNote.title(), validNote.content());

        NoteResource response = NoteResource.fromEntity(noteService.save(noteEntity));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update note by id")
    @PutMapping("/{id}")
    public ResponseEntity<NoteResource> updateNote(@PathVariable Long id, @RequestBody UpdateNoteResource request) {

        var validNote = validator.validate(request);

        var note = new Note(validNote.title(), validNote.content());

        NoteResource response = NoteResource.fromEntity(noteService.update(id, note));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete note by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.deleteById(id));
    }

    @Operation(summary = "Archive note by id")
    @PatchMapping("/{id}/archive")
    public ResponseEntity<Note> archiveNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.archiveById(id));
    }

    @Operation(summary = "Unarchive note by id")
    @PatchMapping("/{id}/unarchive")
    public ResponseEntity<Note> unarchiveNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.unarchiveById(id));
    }

    @Operation(summary = "Add category to note by note id and category to add id")
    @PatchMapping("/{noteId}/add-category/{categoryId}")
    public ResponseEntity<Note> addCategory(@PathVariable Long noteId, @PathVariable Long categoryId) {
        return ResponseEntity.ok(noteService.addCategory(noteId, categoryId));
    }
    @Operation(summary = "Remove category to note by note id and category to remove id")
    @PatchMapping("/{noteId}/remove-category/{categoryId}")
    public ResponseEntity<Note> removeCategory(@PathVariable Long noteId, @PathVariable Long categoryId) {
        return ResponseEntity.ok(noteService.removeCategory(noteId, categoryId));
    }
    @Operation(summary = "Get notes by category")
    @GetMapping("/categories/{categoriesId}")
    public ResponseEntity<List<Note>> getNotesByCategory(@PathVariable List<Long> categoriesId) {
        return ResponseEntity.ok(noteService.findByCategory(categoriesId));
    }



}
