package com.notesapp.notes_backend.mapper;

import com.notesapp.notes_backend.dto.request.NoteRequestDto;
import com.notesapp.notes_backend.dto.response.NoteResponseDto;
import com.notesapp.notes_backend.entity.Note;

import java.time.LocalDateTime;

public class NoteMapper {

    public static Note toEntity(NoteRequestDto dto) {

        return Note.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .pinned(dto.getPinned())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static NoteResponseDto toResponseDto(Note note) {

        return NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .pinned(note.getPinned())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }
}