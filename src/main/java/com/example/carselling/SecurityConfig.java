package com.example.carselling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/registration",
                                "/about",
                                "/styles.css",
                                "/buttonShow.js",
                                "/submitLog",
                                "/submitReg",
                                "/error",
                                "/search",
                                "/wall",
                                "/favicon.ico")
                        .permitAll()
                        .requestMatchers(
                                "/search/select",
                                "/records")
                        .authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/dashboard/**").hasAnyRole("MANAGER", "ADMIN")
                        .anyRequest().denyAll()
                        )
                        .formLogin(form -> form
                                .loginPage("/login?requireAuth=true")
                                .loginProcessingUrl("/submitLog")
                                .usernameParameter("email")
                                .defaultSuccessUrl("/")
                        )
                        .logout(logout -> logout
                                .logoutSuccessUrl("/")
                                .permitAll()
                        )
                        .exceptionHandling(handling -> handling
                                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                                    response.sendRedirect("/wall");})));
        return http.build();
    }
}