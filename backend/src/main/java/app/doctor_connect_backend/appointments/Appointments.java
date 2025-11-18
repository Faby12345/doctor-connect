package app.doctor_connect_backend.appointments;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointments {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "patient_id" , nullable = false)
    private UUID patientId;

    @Column(name = "doctor_id" , nullable = false)
    private UUID doctorId;

    @Column(name = "date" , nullable = false)
    private String date;

    @Column(name = "time" , nullable = false)
    private String time;

    @Column(name = "status" , nullable = false)
    private String status;

}
