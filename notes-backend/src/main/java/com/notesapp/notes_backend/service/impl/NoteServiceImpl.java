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
}