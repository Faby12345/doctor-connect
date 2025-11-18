package app.doctor_connect_backend.doctor;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;


public record DoctorDTO(
        UUID id,
        String fullName,
        String speciality,
        String bio,
        String city,
        int priceMinCents,
        int priceMaxCents,
        boolean verified,
        BigDecimal ratingAvg,
        int ratingCount){}