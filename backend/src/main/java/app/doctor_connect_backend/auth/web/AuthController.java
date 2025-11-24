package app.doctor_connect_backend.auth.web;

import app.doctor_connect_backend.auth.app.AuthService;
import app.doctor_connect_backend.auth.web.DTOs.AuthResponse;
import app.doctor_connect_backend.auth.web.DTOs.LoginRequest;
import app.doctor_connect_backend.auth.web.DTOs.RegisterRequest;
import app.doctor_connect_backend.auth.web.DTOs.UserResponse;
import app.doctor_connect_backend.user.Roles;
import app.doctor_connect_backend.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(loginRequest.email(), loginRequest.password());
        response.addCookie(createAuthCookie(authResponse.token()));
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest,
            HttpServletResponse response) {
        AuthResponse authResponse = authService.register(registerRequest.fullName(), registerRequest.email(),
                registerRequest.password(), registerRequest.role());
        response.addCookie(createAuthCookie(authResponse.token()));
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        User user = (User) authentication.getPrincipal();

        if (user.getUserRole() == Roles.DOCTOR) {
            // provide info
        }
        if (user.getUserRole() == Roles.PATIENT) {
            // provide info
        }
        if (user.getUserRole() == Roles.ADMIN) {
            // provide info
        }

        var res = new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getUserRole(),
                user.getCreatedAt());
        return ResponseEntity.ok(res);
    }

    private Cookie createAuthCookie(String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 24 hours
        return cookie;
    }
}
