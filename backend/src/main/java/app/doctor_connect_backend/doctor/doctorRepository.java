package app.doctor_connect_backend.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import app.doctor_connect_backend.doctor.Doctor;
public interface doctorRepository extends JpaRepository <Doctor, UUID>{
    Doctor findByUserId(UUID id);
}
