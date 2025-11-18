package app.doctor_connect_backend.doctor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import app.doctor_connect_backend.doctor.Doctor;

@Service
public class DoctorService {
    private final doctorRepository doctorRepository;
    public DoctorService(doctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    public Doctor findByUser_id(UUID id) {
        return doctorRepository.findByUserId(id);
    }
    public List<DoctorDTO> findAll() {
        return doctorRepository.findAll().stream()
                .map(d -> new DoctorDTO(
                        d.getUserId(),
                        null, // or fetch fullName if available
                        d.getSpeciality(),
                        d.getBio(),
                        d.getCity(),
                        d.getPriceMinCents(),
                        d.getPriceMaxCents(),
                        d.isVerified(),
                        d.getRatingAvg(),
                        d.getRatingCount()
                ))
                .toList();

    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
