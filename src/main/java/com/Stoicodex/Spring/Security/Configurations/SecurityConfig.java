package com.Stoicodex.Spring.Security.Configurations;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import com.Stoicodex.Spring.Security.ServiceImpl.CustomUserDetailsService;
import com.Stoicodex.Spring.Security.Services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF if using JWT
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Allow access to Swagger endpoints
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/user/register", "/api/user/save").permitAll()
                        .requestMatchers("/api/auth/login" ,"/api/auth/logout").permitAll()
                        .requestMatchers("/api/user/**").hasRole("USER")// Public endpoints
                        .requestMatchers("/api/protected/**").authenticated() // Protected endpoints
                        .requestMatchers("/api/user/**").hasRole("USER") // User endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin endpoints
                        .requestMatchers("/api/manager/**").hasRole("MANAGER") // Manager endpoints
                        .requestMatchers("/api/superadmin/**").hasRole("SUPER_ADMIN") // Super Admin endpoints
                        .requestMatchers("/api/**").authenticated() // All other endpoints
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless sessions
                );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Set allowed origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}