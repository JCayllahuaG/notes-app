package com.jcayllahua.backend.controller.resources;


import com.jcayllahua.backend.model.entity.Note;

import java.util.List;


public record NoteResource(Long id,String title, String content, boolean archived, List<CategoryResource> categories) {


    public static NoteResource fromEntity(Note note) {

        return new NoteResource(note.getId(),note.getTitle(), note.getContent(), note.isArchived(),
                CategoryResource.fromEntity(note.getCategories().stream().toList())
        );
    }
    public static List<NoteResource> fromEntity(List<Note> notes) {
        return notes.stream().map(NoteResource::fromEntity).toList();
    }
}
