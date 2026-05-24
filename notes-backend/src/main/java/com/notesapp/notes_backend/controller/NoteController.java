package com.notesapp.notes_backend.controller;

import com.notesapp.notes_backend.dto.request.NoteRequestDto;
import com.notesapp.notes_backend.dto.response.NoteResponseDto;
import com.notesapp.notes_backend.service.NoteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.notesapp.notes_backend.dto.response.PaginatedResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public NoteResponseDto createNote(@Valid @RequestBody NoteRequestDto dto) {

        return noteService.createNote(dto);
    }

    @GetMapping
    public PaginatedResponseDto getAllNotes(

            @RequestParam(
                    value = "pageNo",
                    defaultValue = "0",
                    required = false
            ) int pageNo,

            @RequestParam(
                    value = "pageSize",
                    defaultValue = "5",
                    required = false
            ) int pageSize,

            @RequestParam(
                    value = "sortBy",
                    defaultValue = "id",
                    required = false
            ) String sortBy,

            @RequestParam(
                    value = "sortDir",
                    defaultValue = "asc",
                    required = false
            ) String sortDir
    ) {

        return noteService.getAllNotes(
                pageNo,
                pageSize,
                sortBy,
                sortDir
        );
    }

    @GetMapping("/search")
    public List<NoteResponseDto> searchNotes(
            @RequestParam String keyword
    ) {

        return noteService.searchNotes(keyword);
    }

    @GetMapping("/{id}")
    public NoteResponseDto getNoteById(@PathVariable Long id) {

        return noteService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public NoteResponseDto updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequestDto dto) {

        return noteService.updateNote(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable Long id) {

        noteService.deleteNote(id);

        return "Note deleted successfully";
    }

    @GetMapping("/pinned")
    public List<NoteResponseDto> getPinnedNotes() {

        return noteService.getPinnedNotes();
    }
}