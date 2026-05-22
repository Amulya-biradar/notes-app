package com.notesapp.notes_backend.repository.impl;

import com.notesapp.notes_backend.entity.Note;
import com.notesapp.notes_backend.repository.JpaNoteRepository;
import com.notesapp.notes_backend.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoteRepositoryImpl implements NoteRepository {

    private final JpaNoteRepository jpaNoteRepository;

    @Override
    public Note save(Note note) {
        return jpaNoteRepository.save(note);
    }

    @Override
    public List<Note> findAll() {
        return jpaNoteRepository.findAll();
    }

    @Override
    public Optional<Note> findById(Long id) {
        return jpaNoteRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaNoteRepository.deleteById(id);
    }
}