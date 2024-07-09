package com.jcayllahua.backend.service.impl;

import com.jcayllahua.backend.model.entity.Category;
import com.jcayllahua.backend.model.entity.Note;
import com.jcayllahua.backend.persistence.repository.CategoryRepository;
import com.jcayllahua.backend.persistence.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SeedInitialDataService {
    private final CategoryRepository categoryRepository;
    private final NoteRepository noteRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SeedInitialDataService.class);

    public SeedInitialDataService(CategoryRepository categoryRepository, NoteRepository noteRepository) {
        this.categoryRepository = categoryRepository;
        this.noteRepository = noteRepository;
    }


    public void seedNotes() {

        List<Note> notes = List.of(
                new Note("Meeting Notes", "Discussion points from today's meeting."),
                new Note("Project Update", "Progress update on project X."),
                new Note("Research Findings", "Key findings from research conducted."),
                new Note("Ideas for UI Revamp", "Brainstorming ideas for the new UI."),
                new Note("Action Items", "List of action items for the team.")
        );

        noteRepository.saveAll(notes);


    }
    public void seedCategories() {

        List<Category> categories = List.of(
                new Category("Work", "#FF5733"),
                new Category("Personal", "#33FF57"),
                new Category("Ideas", "#5733FF"),
                new Category("Research", "#FF5733"),
                new Category("Meetings", "#33FF57")
        );

        categoryRepository.saveAll(categories);

    }


    @EventListener
    public void on(ApplicationReadyEvent event){
        LOGGER.info("{} started.",event.getApplicationContext().getApplicationName());
        LOGGER.info("Starting seeding data");
        seedCategories();
        seedNotes();
        LOGGER.info("Seeding data completed ");

    }
}
