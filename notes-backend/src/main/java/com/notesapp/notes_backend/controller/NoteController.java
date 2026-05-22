package com.notesapp.notes_backend.controller;

import com.notesapp.notes_backend.dto.request.NoteRequestDto;
import com.notesapp.notes_backend.dto.response.NoteResponseDto;
import com.notesapp.notes_backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public NoteResponseDto createNote(@RequestBody NoteRequestDto dto) {

        return noteService.createNote(dto);
    }

    @GetMapping
    public List<NoteResponseDto> getAllNotes() {

        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public NoteResponseDto getNoteById(@PathVariable Long id) {

        return noteService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public NoteResponseDto updateNote(
            @PathVariable Long id,
            @RequestBody NoteRequestDto dto) {

        return noteService.updateNote(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable Long id) {

        noteService.deleteNote(id);

        return "Note deleted successfully";
    }
}