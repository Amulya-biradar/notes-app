package com.notesapp.notes_backend.service.impl;

import com.notesapp.notes_backend.dto.request.NoteRequestDto;
import com.notesapp.notes_backend.dto.response.NoteResponseDto;
import com.notesapp.notes_backend.entity.Note;
import com.notesapp.notes_backend.mapper.NoteMapper;
import com.notesapp.notes_backend.repository.NoteRepository;
import com.notesapp.notes_backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.notesapp.notes_backend.exception.ResourceNotFoundException;
import com.notesapp.notes_backend.dto.response.PaginatedResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public PaginatedResponseDto getAllNotes(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Note> notesPage = noteRepository.findAll(pageable);

        List<Note> notes = notesPage.getContent();

        List<NoteResponseDto> content = notes.stream()
                .map(NoteMapper::toResponseDto)
                .toList();

        PaginatedResponseDto response = new PaginatedResponseDto();

        response.setContent(content);
        response.setPageNo(notesPage.getNumber());
        response.setPageSize(notesPage.getSize());
        response.setTotalElements(notesPage.getTotalElements());
        response.setTotalPages(notesPage.getTotalPages());
        response.setLast(notesPage.isLast());

        return response;
    }

    @Override
    public List<NoteResponseDto> searchNotes(String keyword) {

        List<Note> notes = noteRepository.searchNotes(keyword);

        return notes.stream()
                .map(NoteMapper::toResponseDto)
                .toList();
    }

    @Override
    public NoteResponseDto getNoteById(Long id) {

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

        return NoteMapper.toResponseDto(note);
    }

    @Override
    public NoteResponseDto updateNote(Long id, NoteRequestDto dto) {

        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

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
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

        noteRepository.deleteById(existingNote.getId());
    }

    @Override
    public List<NoteResponseDto> getPinnedNotes() {

        List<Note> notes = noteRepository.findPinnedNotes();

        return notes.stream()
                .map(NoteMapper::toResponseDto)
                .toList();
    }
}