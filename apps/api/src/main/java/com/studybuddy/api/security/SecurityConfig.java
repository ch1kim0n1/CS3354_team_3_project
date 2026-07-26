package com.studybuddy.api.security;

import com.studybuddy.api.domain.AccountStatus;
import com.studybuddy.api.repo.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
  @Bean UserDetailsService userDetailsService(UserRepository users) {
    return email -> users.findByEmailIgnoreCase(email).filter(u -> u.getStatus() == AccountStatus.ACTIVE)
      .map(u -> User.withUsername(u.getEmail()).password(u.getPasswordHash()).authorities(new SimpleGrantedAuthority("ROLE_" + u.getRole().name())).build())
      .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
  }
  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(Customizer.withDefaults())
      .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/index.html", "/assets/**", "/favicon.ico", "/login", "/register", "/onboarding", "/matches", "/groups", "/admin", "/api/v1/auth/**", "/v3/api-docs/**", "/api/docs", "/api/docs/**", "/api/swagger-ui/**", "/actuator/health").permitAll().anyRequest().authenticated())
      .httpBasic(httpBasic -> httpBasic.disable())
      .formLogin(form -> form.disable())
      .logout(logout -> logout.disable())
      .build();
  }
}
