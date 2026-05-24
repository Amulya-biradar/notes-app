package com.notesapp.notes_backend.controller;

import com.notesapp.notes_backend.dto.request.RegisterRequestDto;
import com.notesapp.notes_backend.dto.response.AuthResponseDto;
import com.notesapp.notes_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.notesapp.notes_backend.dto.request.LoginRequestDto;
import com.notesapp.notes_backend.dto.response.LoginResponseDto;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDto register(
            @Valid @RequestBody RegisterRequestDto request
    ) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDto login(
            @Valid @RequestBody LoginRequestDto request
    ) {

        return authService.login(request);
    }
}