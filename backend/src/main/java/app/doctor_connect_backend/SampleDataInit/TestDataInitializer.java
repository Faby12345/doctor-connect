package app.doctor_connect_backend.SampleDataInit;

import app.doctor_connect_backend.doctor.AddDoctorsForTesting;
import app.doctor_connect_backend.doctor.DoctorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer implements CommandLineRunner {

    private final AddDoctorsForTesting seeder;
    private final DoctorService doctorService;

    public TestDataInitializer(AddDoctorsForTesting seeder, DoctorService doctorService) {
        this.seeder = seeder;
        this.doctorService = doctorService;
    }

    @Override
    public void run(String... args) {
        try {
            if (doctorService.findAll().isEmpty()) {
                System.out.println("🩺 No doctors found — seeding test data...");
                seeder.addDoctors();
            } else {
                System.out.println("✅ Doctors already exist — skipping test data seeding.");
            }
        } catch (Exception e) {
            System.err.println("❌ Seeding failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}