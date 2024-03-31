package com.demo.configuration;

import com.demo.services.security.LogoutHandlerService;
import com.demo.services.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author 165139
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${config.api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   AuthenticationProvider authenticationProvider,
                                                   LogoutHandlerService logoutHandlerService) throws Exception {
        return http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/").permitAll()

                                .requestMatchers(HttpMethod.POST, joinPath("auth", "**")).permitAll()

                                .requestMatchers(joinPath("users", "**")).hasRole(Roles.TEACHER.name())

                                .requestMatchers(HttpMethod.PUT, joinPath("quizzes", "submit")).hasRole(Roles.STUDENT.name())

                                .requestMatchers(HttpMethod.GET, joinPath("quizzes", "**")).hasAnyRole(Roles.TEACHER.name(), Roles.STUDENT.name())

                                .requestMatchers(HttpMethod.POST, joinPath("quizzes", "**")).hasRole(Roles.TEACHER.name())

                                .requestMatchers(HttpMethod.PUT, joinPath("quizzes", "**")).hasRole(Roles.TEACHER.name())

                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .logout(logout -> logout.logoutUrl(joinPath("auth", "logout"))
                        .addLogoutHandler(logoutHandlerService)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .build();
    }

    private String joinPath(String... suffix) {
        return String.format("%s/%s", this.apiPrefix, String.join("/", suffix));
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsServiceImpl userDetailsService) {
        return new ProviderManager(authenticationProvider(userDetailsService));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    enum Roles {
        TEACHER, STUDENT
    }

}