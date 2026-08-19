package Group.com.starmerch.Artifact.star_merch_hub.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import Group.com.starmerch.Artifact.star_merch_hub.model.Role;
import Group.com.starmerch.Artifact.star_merch_hub.model.User;
import Group.com.starmerch.Artifact.star_merch_hub.repository.UserRepository;

@Configuration
public class UserDataInitializer {

    @Bean
    CommandLineRunner createDemoUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!userRepository.existsByUsername("admin")) {

                User admin = new User(
                    "admin",
                    "admin@littlesun.com",
                    passwordEncoder.encode("Admin123!"),
                    Role.ADMIN
                );

                userRepository.save(admin);
            }

            if (!userRepository.existsByUsername("staff")) {

                User staff = new User(
                    "staff",
                    "staff@littlesun.com",
                    passwordEncoder.encode("Staff123!"),
                    Role.STAFF
                );

                userRepository.save(staff);
            }

            if (!userRepository.existsByUsername("customer")) {

                User customer = new User(
                    "customer",
                    "customer@littlesun.com",
                    passwordEncoder.encode("Customer123!"),
                    Role.CUSTOMER
                );

                userRepository.save(customer);
            }
        };
    }
}