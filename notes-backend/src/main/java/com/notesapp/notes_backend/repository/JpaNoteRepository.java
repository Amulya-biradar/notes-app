package com.notesapp.notes_backend.repository;

import com.notesapp.notes_backend.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaNoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String titleKeyword,
            String contentKeyword
    );

    List<Note> findByPinnedTrue();
}
