package com.notesapp.notes_backend.repository;

import com.notesapp.notes_backend.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);
}