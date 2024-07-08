package com.jcayllahua.backend.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;
    private String content;
    private boolean archived;

    @ManyToMany
    @JoinTable(
            name = "note_category",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference
    private Set<Category> categories = new HashSet<>();


    public Note() {
        this.archived = false;
    }

    public Note (String title, String content) {
        this();
        this.title = title;
        this.content = content;

    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    public Note updateNote (Note note) {
        this.title = note.getTitle();
        this.content = note.getContent();
        return this;
    }
    public Note archiveNote() {
        this.archived = true;
        return this;
    }
    public Note unarchiveNote() {
        this.archived = false;
        return this;
    }

}
