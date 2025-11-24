package app.doctor_connect_backend.auth.app;

import app.doctor_connect_backend.auth.security.JwtUtil;
import app.doctor_connect_backend.auth.web.DTOs.AuthResponse;
import app.doctor_connect_backend.user.Roles;
import app.doctor_connect_backend.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(PasswordEncoder encoder, UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(String email, String password) {
        email = email.trim().toLowerCase();
        try {
            var user = userService.findEmail(email);
            if (encoder.matches(password, user.getPasswordHash())) {
                String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getUserRole().name());
                return new AuthResponse(token, user.getId(), user.getFullName(), user.getEmail(), user.getUserRole(),
                        user.getCreatedAt());
            } else {
                throw new RuntimeException("Invalid password");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public AuthResponse register(String fullName, String email, String password, Roles role) {
        email = email.trim().toLowerCase();

        try {
            String hash = encoder.encode(password);
            var saved = userService.createUser(email, hash, fullName, role);
            if (role == Roles.DOCTOR) {
                // Doctor newDoctor = new Doctor(saved.getId(), "test", "test","test", 1, 1,
                // true, new BigDecimal("4.87"), 4);
                // var savedDoctor = doctorService.save(newDoctor);
            }
            String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getUserRole().name());
            return new AuthResponse(token, saved.getId(), saved.getFullName(), saved.getEmail(), saved.getUserRole(),
                    saved.getCreatedAt());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
