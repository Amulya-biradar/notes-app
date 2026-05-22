package com.notesapp.notes_backend.repository;

import com.notesapp.notes_backend.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    Note save(Note note);

    List<Note> findAll();

    Optional<Note> findById(Long id);

    void deleteById(Long id);
}
