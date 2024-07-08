package com.jcayllahua.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private Set<Note> notes;

    public Category() {
    }

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

}
