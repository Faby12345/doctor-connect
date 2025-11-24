package app.doctor_connect_backend.appointments;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentsService {
    private final AppointmentsRepo appointmentsRepo;

    public AppointmentsService(AppointmentsRepo appointmentsRepo) {
        this.appointmentsRepo = appointmentsRepo;
    }

    public @NonNull Appointments save(Appointments appointments) {
        return appointmentsRepo.save(appointments);
    }

    public List<Appointments> GetAllAppointmentsDoctor(UUID doctorId) {
        return appointmentsRepo.findAllByDoctorId(doctorId).stream().toList();
    }

    public List<Appointments> GetAllAppointmentsPatient(UUID patientId) {
        return appointmentsRepo.findAllByPatientId(patientId).stream().toList();
    }

    public Boolean CancelAppointment(@NonNull UUID id) {
        Appointments app = appointmentsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        app.setStatus("Canceled");
        appointmentsRepo.save(app);
        return true;
    }

    public Boolean AcceptAppointment(@NonNull UUID id) {
        Appointments app = appointmentsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        app.setStatus("Confirmed");
        appointmentsRepo.save(app);
        return true;
    }

}
