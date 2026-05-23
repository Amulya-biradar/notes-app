package com.notesapp.notes_backend.service;

import com.notesapp.notes_backend.dto.request.NoteRequestDto;
import com.notesapp.notes_backend.dto.response.NoteResponseDto;
import com.notesapp.notes_backend.dto.response.PaginatedResponseDto;

import java.util.List;

public interface NoteService {

    NoteResponseDto createNote(NoteRequestDto dto);

    PaginatedResponseDto getAllNotes(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir
    );

    NoteResponseDto getNoteById(Long id);

    NoteResponseDto updateNote(Long id, NoteRequestDto dto);

    void deleteNote(Long id);

    List<NoteResponseDto> searchNotes(String keyword);

    List<NoteResponseDto> getPinnedNotes();
}