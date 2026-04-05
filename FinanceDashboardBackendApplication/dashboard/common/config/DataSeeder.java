package com.finance.dashboard.common.config;

import com.finance.dashboard.user.entity.Role;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.enums.RoleName;
import com.finance.dashboard.user.enums.UserStatus;
import com.finance.dashboard.user.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(User.builder()
                .name("System Admin")
                .email("admin@finance.com")
                .password(passwordEncoder.encode("Admin@123"))
                .status(UserStatus.ACTIVE)
                .roles(Set.of(Role.builder().name(RoleName.ADMIN).build()))
                .build());

        userRepository.save(User.builder()
                .name("Finance Analyst")
                .email("analyst@finance.com")
                .password(passwordEncoder.encode("Analyst@123"))
                .status(UserStatus.ACTIVE)
                .roles(Set.of(Role.builder().name(RoleName.ANALYST).build()))
                .build());

        userRepository.save(User.builder()
                .name("Dashboard Viewer")
                .email("viewer@finance.com")
                .password(passwordEncoder.encode("Viewer@123"))
                .status(UserStatus.ACTIVE)
                .roles(Set.of(Role.builder().name(RoleName.VIEWER).build()))
                .build());
    }
}
