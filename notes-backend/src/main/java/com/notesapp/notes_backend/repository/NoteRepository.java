package com.notesapp.notes_backend.repository;

import com.notesapp.notes_backend.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    Note save(Note note);

    List<Note> findAll();

    Page<Note> findAll(Pageable pageable);

    Optional<Note> findById(Long id);

    void deleteById(Long id);

    List<Note> searchNotes(String keyword);

    List<Note> findPinnedNotes();
}
