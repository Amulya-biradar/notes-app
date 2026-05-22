package com.notesapp.notes_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteResponseDto {

    private Long id;

    private String title;

    private String content;

    private Boolean pinned;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}