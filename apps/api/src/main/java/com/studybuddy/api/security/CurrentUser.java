package com.studybuddy.api.security;

import com.studybuddy.api.domain.User;
import com.studybuddy.api.repo.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
  private final UserRepository users;
  public CurrentUser(UserRepository users) { this.users = users; }
  public User require(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) throw new AccessDeniedException("Authentication required");
    return users.findByEmailIgnoreCase(authentication.getName()).orElseThrow(() -> new AccessDeniedException("Account no longer exists"));
  }
}
