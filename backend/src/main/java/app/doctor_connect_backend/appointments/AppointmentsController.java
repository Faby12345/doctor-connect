package app.doctor_connect_backend.appointments;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentsController {
    private final AppointmentsService appointmentsService;
    private final AppointmentsRepo appointmentsRepo;
    public AppointmentsController(AppointmentsService appointmentsService, AppointmentsRepo appointmentsRepo) {
        this.appointmentsService = appointmentsService;
        this.appointmentsRepo = appointmentsRepo;
    }
    @PostMapping
    public ResponseEntity<Appointments> create(@RequestBody AppointmentsDTO dto,
                                               HttpServletRequest http) {

        var session = http.getSession(false);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID patientId = (UUID) session.getAttribute("uid");
        if (patientId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var app = new Appointments();

        app.setPatientId(patientId);
        app.setDoctorId(dto.doctorId());
        app.setDate(dto.date());
        app.setTime(dto.time());
        app.setStatus("Pending");

        Appointments saved = appointmentsRepo.save(app);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping(value = "doctor/{id}")
    public ResponseEntity<List<Appointments>> getAppForDoc(@PathVariable UUID id){

        try{
            var res = appointmentsService.GetAllAppointmentsDoctor(id);
            return ResponseEntity.ok(res);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }




}
