package com.notesapp.notes_backend.repository;

import com.notesapp.notes_backend.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNoteRepository extends JpaRepository<Note, Long> {

}
