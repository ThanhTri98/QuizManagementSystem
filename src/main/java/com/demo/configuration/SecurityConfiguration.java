package com.demo.configuration;

import com.demo.services.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author 165139
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/users").hasRole(Roles.TEACHER.name())
                                .requestMatchers(HttpMethod.GET, "**/quizzes").hasAnyRole(Roles.TEACHER.name(), Roles.STUDENT.name())
                                .requestMatchers(HttpMethod.POST, "**/quizzes").hasAnyRole(Roles.TEACHER.name(), Roles.STUDENT.name())
                                .requestMatchers("/").permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserDetailsServiceImpl userDetailsService) {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    enum Roles {
        TEACHER, STUDENT
    }

}