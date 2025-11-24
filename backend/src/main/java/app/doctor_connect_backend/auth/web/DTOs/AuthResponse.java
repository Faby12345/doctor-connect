package app.doctor_connect_backend.auth.web.DTOs;

import app.doctor_connect_backend.user.Roles;

import java.time.Instant;
import java.util.UUID;

public record AuthResponse(
        String token,
        UUID id,
        String fullName,
        String email,
        Roles role,
        Instant createdAt) {
}
