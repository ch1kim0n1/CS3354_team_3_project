package com.studybuddy.api.domain;

import jakarta.persistence.*;

@Entity @Table(name = "student_profiles")
public class StudentProfile {
  @Id private Long userId;
  @OneToOne(fetch = FetchType.LAZY) @MapsId @JoinColumn(name = "user_id") private User user;
  @Column(name = "display_name", nullable = false, length = 80) private String displayName;
  @Column(length = 120) private String major;
  @Column(name = "interests_csv", length = 500) private String interestsCsv = "";
  @Column(name = "study_mode", length = 40) private String studyMode = "FLEXIBLE";
  @Column(name = "onboarding_complete", nullable = false) private boolean onboardingComplete;
  protected StudentProfile() {}
  public StudentProfile(User user, String displayName, String major, String interestsCsv, String studyMode) { this.user = user; this.displayName = displayName; this.major = major; this.interestsCsv = interestsCsv == null ? "" : interestsCsv; this.studyMode = studyMode == null ? "FLEXIBLE" : studyMode; }
  public Long getUserId() { return userId; } public User getUser() { return user; } public String getDisplayName() { return displayName; } public String getMajor() { return major; } public String getInterestsCsv() { return interestsCsv; } public String getStudyMode() { return studyMode; } public boolean isOnboardingComplete() { return onboardingComplete; }
  public void update(String displayName, String major, String interestsCsv, String studyMode, boolean complete) { this.displayName=displayName; this.major=major; this.interestsCsv=interestsCsv == null ? "" : interestsCsv; this.studyMode=studyMode; this.onboardingComplete=complete; }
}
