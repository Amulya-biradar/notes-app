package com.notesapp.notes_backend.repository;

import com.notesapp.notes_backend.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import com.notesapp.notes_backend.entity.User;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface JpaNoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String titleKeyword,
            String contentKeyword
    );

    List<Note> findByPinnedTrue();

    List<Note> findByUser(User user);

    Optional<Note> findByIdAndUser(Long id, User user);

    Page<Note> findByUser(User user, Pageable pageable);

    List<Note> findByUserAndPinnedTrue(User user);

    List<Note> findByUserAndTitleContainingIgnoreCaseOrUserAndContentContainingIgnoreCase(
            User user1,
            String titleKeyword,
            User user2,
            String contentKeyword
    );
}
