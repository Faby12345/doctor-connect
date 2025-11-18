package app.doctor_connect_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CryptoConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // default strength = 10 (good for dev). You can raise it later (12–14) for prod.
        return new BCryptPasswordEncoder();
    }
}
