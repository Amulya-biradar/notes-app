package com.notesapp.notes_backend.repository.impl;

import com.notesapp.notes_backend.entity.Note;
import com.notesapp.notes_backend.repository.JpaNoteRepository;
import com.notesapp.notes_backend.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.notesapp.notes_backend.entity.User;

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
    public Page<Note> findAll(Pageable pageable) {
        return jpaNoteRepository.findAll(pageable);
    }

    @Override
    public Optional<Note> findById(Long id) {
        return jpaNoteRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaNoteRepository.deleteById(id);
    }

    @Override
    public List<Note> searchNotes(String keyword) {

        return jpaNoteRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }

    @Override
    public List<Note> findPinnedNotes() {

        return jpaNoteRepository.findByPinnedTrue();
    }

    @Override
    public Page<Note> findByUser(User user, Pageable pageable) {
        return jpaNoteRepository.findByUser(user, pageable);
    }

    @Override
    public Optional<Note> findByIdAndUser(Long id, User user) {
        return jpaNoteRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Note> searchNotesByUser(User user, String keyword) {

        return jpaNoteRepository
                .findByUserAndTitleContainingIgnoreCaseOrUserAndContentContainingIgnoreCase(
                        user,
                        keyword,
                        user,
                        keyword
                );
    }

    @Override
    public List<Note> findPinnedNotesByUser(User user) {
        return jpaNoteRepository.findByUserAndPinnedTrue(user);
    }
}