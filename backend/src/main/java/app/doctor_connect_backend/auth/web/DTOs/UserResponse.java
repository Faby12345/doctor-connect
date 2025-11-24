package app.doctor_connect_backend.auth.web.DTOs;

import app.doctor_connect_backend.user.Roles;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(UUID id, String name, String email, Roles role, Instant createdAt) {
}
