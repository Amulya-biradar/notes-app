package com.notesapp.notes_backend.repository;

import com.notesapp.notes_backend.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.notesapp.notes_backend.entity.User;

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

    Page<Note> findByUser(User user, Pageable pageable);

    Optional<Note> findByIdAndUser(Long id, User user);

    List<Note> searchNotesByUser(User user, String keyword);

    List<Note> findPinnedNotesByUser(User user);
}
