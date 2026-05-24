package com.notesapp.notes_backend.service;

import com.notesapp.notes_backend.dto.request.RegisterRequestDto;
import com.notesapp.notes_backend.dto.response.AuthResponseDto;
import com.notesapp.notes_backend.dto.request.LoginRequestDto;
import com.notesapp.notes_backend.dto.response.LoginResponseDto;

public interface AuthService {

    AuthResponseDto register(
            RegisterRequestDto request
    );

    LoginResponseDto login(
            LoginRequestDto request
    );
}