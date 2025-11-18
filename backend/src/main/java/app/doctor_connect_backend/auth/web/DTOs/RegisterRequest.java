package app.doctor_connect_backend.auth.web.DTOs;

import app.doctor_connect_backend.user.Roles;

public record RegisterRequest(String fullName, String email, String password, Roles role) {
}
