package app.doctor_connect_backend.auth.app;

import app.doctor_connect_backend.doctor.Doctor;
import app.doctor_connect_backend.doctor.DoctorService;

import app.doctor_connect_backend.auth.web.DTOs.UserResponse;
import app.doctor_connect_backend.user.Roles;
import app.doctor_connect_backend.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {
    private final UserService userService;
    private final DoctorService doctorService;
    private final PasswordEncoder encoder;

    public AuthService(PasswordEncoder encoder, UserService userService, DoctorService doctorService) {
        this.userService = userService;
        this.encoder = encoder;
        this.doctorService = doctorService;
    }
    public UserResponse login(String email, String password) {
       email = email.trim().toLowerCase();
        try {
            var user = userService.findEmail(email);
            if (encoder.matches(password, user.getPasswordHash())) {
                return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getUserRole(), user.getCreatedAt());
            } else {
                throw new RuntimeException("Invalid password");
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public UserResponse register(String fullName, String email, String password, Roles role){
        email = email.trim().toLowerCase();

        try{
            String hash = encoder.encode(password);
            var saved = userService.createUser(email, hash, fullName, role);
            if(role == Roles.DOCTOR){
                //Doctor newDoctor = new Doctor(saved.getId(), "test", "test","test", 1, 1, true, new BigDecimal("4.87"), 4);
                //var savedDoctor = doctorService.save(newDoctor);
            }
            return new UserResponse(saved.getId(), saved.getFullName(), saved.getEmail(), saved.getUserRole(), saved.getCreatedAt( ));
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }

    }


}
