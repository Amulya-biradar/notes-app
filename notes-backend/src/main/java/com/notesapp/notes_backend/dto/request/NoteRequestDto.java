package com.notesapp.notes_backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteRequestDto {

    private String title;

    private String content;

    private Boolean pinned;
}