package com.notesapp.notes_backend.service.impl;

import com.notesapp.notes_backend.dto.request.NoteRequestDto;
import com.notesapp.notes_backend.dto.response.NoteResponseDto;
import com.notesapp.notes_backend.entity.Note;
import com.notesapp.notes_backend.mapper.NoteMapper;
import com.notesapp.notes_backend.repository.NoteRepository;
import com.notesapp.notes_backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public NoteResponseDto createNote(NoteRequestDto dto) {

        Note note = NoteMapper.toEntity(dto);

        Note savedNote = noteRepository.save(note);

        return NoteMapper.toResponseDto(savedNote);
    }

    @Override
    public List<NoteResponseDto> getAllNotes() {

        List<Note> notes = noteRepository.findAll();

        return notes.stream()
                .map(NoteMapper::toResponseDto)
                .toList();
    }
    @Override
    public NoteResponseDto getNoteById(Long id) {

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        return NoteMapper.toResponseDto(note);
    }

    @Override
    public NoteResponseDto updateNote(Long id, NoteRequestDto dto) {

        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        existingNote.setTitle(dto.getTitle());
        existingNote.setContent(dto.getContent());
        existingNote.setPinned(dto.getPinned());
        existingNote.setUpdatedAt(java.time.LocalDateTime.now());

        Note updatedNote = noteRepository.save(existingNote);

        return NoteMapper.toResponseDto(updatedNote);
    }

    @Override
    public void deleteNote(Long id) {

        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        noteRepository.deleteById(existingNote.getId());
    }
}