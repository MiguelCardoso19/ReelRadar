package com.project.reelRadar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SecurityFilter securityFilter;

    /**
     * Configures the Spring Security filter chain.
     *
     * This method defines the security policies for the application using Spring Security.
     * It utilizes the `HttpSecurity` object to configure various aspects like:
     *  - Authentication: Defines how users prove their identity (e.g., username/password, tokens)
     *  - Authorization: Specifies which actions users are allowed to perform based on roles or permissions
     *  - Session Management: Determines how user sessions are handled (e.g., cookies, tokens) - typically stateless for REST APIs
     *  - CSRF Protection: Protects against Cross-Site Request Forgery attacks (be cautious when disabling)
     *
     * @param http The HttpSecurity object used to configure Spring Security filters.
     * @return The SecurityFilterChain object representing the configured filter chain.
     * @throws Exception If any errors occur during security filter chain configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/favorites/{userId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/favorites/{userId}/add").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/favorites/{userId}/remove").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}