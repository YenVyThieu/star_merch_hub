package Group.com.starmerch.Artifact.star_merch_hub.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // H2 CONSOLE SECURITY
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain h2ConsoleSecurityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .securityMatcher(PathRequest.toH2Console())

            .authorizeHttpRequests(auth ->
                auth.anyRequest().permitAll()
            )

            .csrf(CsrfConfigurer::disable)

            .headers(headers ->
                headers.frameOptions(
                    FrameOptionsConfig::sameOrigin
                )
            );

        return http.build();
    }


    // MAIN APPLICATION SECURITY
    @Bean
    public SecurityFilterChain appSecurityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/",
                    "/about",
                    "/artists",
                    "/login",
                    "/register",
                    "/access-denied",
                    "/css/**"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.GET,
                    "/products"
                ).permitAll()

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/products/new",
                    "/products/*/edit"
                )
                .hasAnyRole("STAFF", "ADMIN")

                .requestMatchers(
                    HttpMethod.POST,
                    "/products",
                    "/products/*/edit"
                )
                .hasAnyRole("STAFF", "ADMIN")

                .requestMatchers(
                    HttpMethod.POST,
                    "/products/*/delete"
                )
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            .exceptionHandling(exception ->
                exception.accessDeniedPage(
                    "/access-denied"
                )
            );

        return http.build();
    }
}