package com.notesapp.notes_backend.service;

import com.notesapp.notes_backend.dto.request.NoteRequestDto;
import com.notesapp.notes_backend.dto.response.NoteResponseDto;

import java.util.List;

public interface NoteService {

    NoteResponseDto createNote(NoteRequestDto dto);

    List<NoteResponseDto> getAllNotes();

    NoteResponseDto getNoteById(Long id);

    NoteResponseDto updateNote(Long id, NoteRequestDto dto);
}