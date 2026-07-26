package com.studybuddy.api.api;

import jakarta.validation.constraints.*;
import java.time.*;
import java.util.List;

public final class ApiDtos {
  private ApiDtos() {}
  public record RegisterRequest(@Email @NotBlank String email, @NotBlank @Size(min=10,max=128) String password, @NotBlank @Size(max=80) String displayName) {}
  public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
  public record MeResponse(Long id,String email,String role,boolean onboardingComplete) {}
  public record ProfileRequest(@NotBlank @Size(max=80) String displayName,@Size(max=120) String major,@Size(max=500) String interestsCsv,@NotBlank @Size(max=40) String studyMode,boolean onboardingComplete) {}
  public record ProfileResponse(Long userId,String displayName,String major,String interestsCsv,String studyMode,boolean onboardingComplete,List<EnrollmentResponse> courses,List<AvailabilityResponse> availability) {}
  public record CourseRequest(@Pattern(regexp="[A-Za-z]{2,8}\\s?-?\\d{3,4}") String code,@NotBlank @Size(max=160) String name,@NotBlank @Size(max=32) String term) {}
  public record EnrollmentResponse(Long courseId,String code,String name,String term) {}
  public record AvailabilityRequest(@NotNull DayOfWeek day,@NotNull LocalTime startTime,@NotNull LocalTime endTime) {}
  public record AvailabilityResponse(DayOfWeek day,LocalTime startTime,LocalTime endTime) {}
  public record MatchResponse(Long userId,String displayName,String major,List<String> sharedCourses,String studyMode,int score,List<String> reasons) {}
  public record GroupRequest(@NotNull Long courseId,@NotBlank @Size(max=100) String name,@NotBlank @Size(max=1000) String description,@Min(2) @Max(100) int capacity,@NotBlank @Size(max=40) String studyMode) {}
  public record GroupResponse(Long id,Long courseId,String courseCode,String name,String description,int capacity,String studyMode,Long ownerId,long approvedMembers,boolean member,boolean canManage) {}
  public record SessionRequest(@NotNull Instant startsAt,@NotNull Instant endsAt,@Size(max=300) String location,@Size(max=500) String virtualUrl,@Size(max=1000) String agenda) {}
  public record SessionResponse(Long id,Instant startsAt,Instant endsAt,String location,String virtualUrl,String agenda) {}
  public record MessageRequest(@NotBlank @Size(max=2000) String body) {}
  public record MessageResponse(Long id,Long authorId,String authorName,String body,Instant createdAt,boolean removed) {}
  public record ReportRequest(@NotBlank @Pattern(regexp="PROFILE|GROUP|MESSAGE") String targetType,@NotNull Long targetId,@NotBlank @Size(max=1000) String reason) {}
  public record ReportResponse(Long id,String targetType,Long targetId,String reason,String status) {}
}
