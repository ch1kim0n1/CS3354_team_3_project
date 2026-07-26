package com.studybuddy.api.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name = "users")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable = false, unique = true, length = 254) private String email;
  @Column(name = "password_hash", nullable = false) private String passwordHash;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private AccountRole role = AccountRole.STUDENT;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private AccountStatus status = AccountStatus.ACTIVE;
  @Column(name = "created_at", nullable = false) private Instant createdAt = Instant.now();
  protected User() {}
  public User(String email, String passwordHash, AccountRole role) { this.email = email.toLowerCase(); this.passwordHash = passwordHash; this.role = role; }
  public Long getId() { return id; } public String getEmail() { return email; } public String getPasswordHash() { return passwordHash; }
  public AccountRole getRole() { return role; } public AccountStatus getStatus() { return status; }
  public void setStatus(AccountStatus status) { this.status = status; }
}
