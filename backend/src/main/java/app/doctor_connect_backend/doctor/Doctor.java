package app.doctor_connect_backend.doctor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctors_profile")
public class Doctor {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "speciality", nullable = false)
    private String speciality;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Column(name = "price_min_cents", nullable = false)
    private int priceMinCents;

    @Column(name = "price_max_cents", nullable = false)
    private int priceMaxCents;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "rating_avg", nullable = false)
    private BigDecimal ratingAvg;

    @Column(name = "rating_count", nullable = false)
    private int ratingCount;
}
