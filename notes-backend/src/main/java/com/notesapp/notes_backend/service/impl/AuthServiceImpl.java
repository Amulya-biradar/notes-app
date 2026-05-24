package com.notesapp.notes_backend.service.impl;

import com.notesapp.notes_backend.dto.request.RegisterRequestDto;
import com.notesapp.notes_backend.dto.response.AuthResponseDto;
import com.notesapp.notes_backend.entity.User;
import com.notesapp.notes_backend.repository.UserRepository;
import com.notesapp.notes_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.notesapp.notes_backend.dto.request.LoginRequestDto;
import com.notesapp.notes_backend.dto.response.LoginResponseDto;
import com.notesapp.notes_backend.service.JwtService;
import com.notesapp.notes_backend.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public AuthResponseDto register(
            RegisterRequestDto request
    ) {

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        userRepository.save(user);

        return new AuthResponseDto(
                "User registered successfully"
        );
    }

    @Override
    public LoginResponseDto login(
            LoginRequestDto request
    ) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid email or password"
                        )
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {

            throw new ResourceNotFoundException(
                    "Invalid email or password"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new LoginResponseDto(token);
    }
}