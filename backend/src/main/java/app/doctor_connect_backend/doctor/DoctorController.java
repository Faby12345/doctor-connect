package app.doctor_connect_backend.doctor;

import app.doctor_connect_backend.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final UserService userService;

    public DoctorController(DoctorService doctorService, UserService userService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<DoctorDTO> findAll() {
        return doctorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable String id) {
        try {
            var doctor = doctorService.findByUser_id(UUID.fromString(id));
            var user = userService.findById(doctor.getUserId());
            DoctorDTO dto = new DoctorDTO(doctor.getUserId(), user.getFullName(), doctor.getSpeciality(),
                    doctor.getBio(), doctor.getCity(), doctor.getPriceMinCents(), doctor.getPriceMaxCents(),
                    doctor.isVerified(), doctor.getRatingAvg(), doctor.getRatingCount());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}
