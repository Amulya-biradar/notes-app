package com.notesapp.notes_backend.service.impl;

import com.notesapp.notes_backend.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12";

    @Override
    public String generateToken(String email) {

        Key key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );

        return Jwts.builder()

                .setSubject(email)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    @Override
    public String extractEmail(String token) {

        return getClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token) {

        try {

            getClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    private Claims getClaims(String token) {

        Key key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}