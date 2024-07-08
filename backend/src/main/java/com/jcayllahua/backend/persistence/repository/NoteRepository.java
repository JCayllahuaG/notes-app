package com.jcayllahua.backend.persistence.repository;


import com.jcayllahua.backend.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

    Optional<List<Note>> findDistinctByCategoriesIdIn(List<Long> categoryIds);
}
