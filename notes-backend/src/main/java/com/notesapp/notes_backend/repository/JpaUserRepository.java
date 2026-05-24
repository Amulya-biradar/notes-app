package com.notesapp.notes_backend.repository;

import com.notesapp.notes_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}