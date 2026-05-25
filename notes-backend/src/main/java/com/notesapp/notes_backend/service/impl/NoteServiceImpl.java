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
import com.notesapp.notes_backend.entity.User;
import com.notesapp.notes_backend.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final UserRepository userRepository;

    @Override
    public NoteResponseDto createNote(NoteRequestDto dto) {

        User user = getLoggedInUser();

        Note note = NoteMapper.toEntity(dto);

        note.setUser(user);

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

        User user = getLoggedInUser();

        Page<Note> notesPage = noteRepository.findByUser(user, pageable);

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

        User user = getLoggedInUser();

        List<Note> notes = noteRepository.searchNotesByUser(user, keyword);

        return notes.stream()
                .map(NoteMapper::toResponseDto)
                .toList();
    }

    @Override
    public NoteResponseDto getNoteById(Long id) {

        User user = getLoggedInUser();

        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Note not found with id: " + id
                        ));

        return NoteMapper.toResponseDto(note);
    }

    @Override
    public NoteResponseDto updateNote(Long id, NoteRequestDto dto) {

        User user = getLoggedInUser();

        Note existingNote = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Note not found with id: " + id
                        ));

        existingNote.setTitle(dto.getTitle());
        existingNote.setContent(dto.getContent());
        existingNote.setPinned(dto.getPinned());
        existingNote.setUpdatedAt(java.time.LocalDateTime.now());

        Note updatedNote = noteRepository.save(existingNote);

        return NoteMapper.toResponseDto(updatedNote);
    }

    @Override
    public void deleteNote(Long id) {

        User user = getLoggedInUser();

        Note existingNote = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Note not found with id: " + id
                        ));

        noteRepository.deleteById(existingNote.getId());
    }

    @Override
    public List<NoteResponseDto> getPinnedNotes() {

        User user = getLoggedInUser();

        List<Note> notes = noteRepository.findPinnedNotesByUser(user);

        return notes.stream()
                .map(NoteMapper::toResponseDto)
                .toList();
    }

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}