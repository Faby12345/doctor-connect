package app.doctor_connect_backend.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentsRepo extends JpaRepository<Appointments, UUID> {
    List<Appointments> findAllByDoctorId(UUID doctorId);
    List<Appointments> findAllByPatientId(UUID patientId);
   // Appointments findByDoctorIdAndPatientId(UUID doctorId, UUID patientId);

}
