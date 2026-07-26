package com.studybuddy.api.service;

import com.studybuddy.api.api.ApiDtos.*;
import com.studybuddy.api.domain.*;
import com.studybuddy.api.repo.*;
import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AccountService {
  private final UserRepository users; private final ProfileRepository profiles; private final EnrollmentRepository enrollments; private final AvailabilityRepository availability; private final CourseRepository courses; private final PasswordEncoder encoder; private final String allowedEmailDomain;
  public AccountService(UserRepository users,ProfileRepository profiles,EnrollmentRepository enrollments,AvailabilityRepository availability,CourseRepository courses,PasswordEncoder encoder,@Value("${app.allowed-email-domain}") String allowedEmailDomain){this.users=users;this.profiles=profiles;this.enrollments=enrollments;this.availability=availability;this.courses=courses;this.encoder=encoder;this.allowedEmailDomain=allowedEmailDomain.toLowerCase();}
  @Transactional public User register(RegisterRequest request){ String email=request.email().trim().toLowerCase(); if(!email.endsWith("@"+allowedEmailDomain)) throw new IllegalArgumentException("Use an approved university email address."); if(users.findByEmailIgnoreCase(email).isPresent()) throw new IllegalArgumentException("An account already exists for that email."); User user=users.save(new User(email,encoder.encode(request.password()),AccountRole.STUDENT)); profiles.save(new StudentProfile(user,request.displayName(),null,"","FLEXIBLE")); return user; }
  @Transactional public ProfileResponse updateProfile(User user,ProfileRequest request){ StudentProfile profile=profiles.findById(user.getId()).orElseThrow(); profile.update(request.displayName(),request.major(),request.interestsCsv(),request.studyMode(),request.onboardingComplete()); return profile(user); }
  @Transactional public ProfileResponse addCourse(User user,CourseRequest request){ Course course=courses.findByCodeIgnoreCase(request.code()).orElseGet(() -> courses.save(new Course(request.code(),request.name()))); if(enrollments.existsByUserIdAndCourseIdAndTerm(user.getId(),course.getId(),request.term())) throw new IllegalArgumentException("That course is already on your profile for this term."); enrollments.save(new CourseEnrollment(user,course,request.term())); return profile(user); }
  @Transactional public ProfileResponse replaceAvailability(User user,List<AvailabilityRequest> request){ for(AvailabilityRequest item:request) if(!item.startTime().isBefore(item.endTime())) throw new IllegalArgumentException("Availability end time must be after start time."); availability.deleteByUserId(user.getId()); availability.saveAll(request.stream().map(a -> new AvailabilityWindow(user,a.day(),a.startTime(),a.endTime())).toList()); return profile(user); }
  @Transactional(readOnly=true) public ProfileResponse profile(User user){ StudentProfile p=profiles.findById(user.getId()).orElseThrow(); List<EnrollmentResponse> courseDtos=enrollments.findByUserIdAndActiveTrue(user.getId()).stream().map(e->new EnrollmentResponse(e.getCourse().getId(),e.getCourse().getCode(),e.getCourse().getName(),e.getTerm())).toList(); List<AvailabilityResponse> times=availability.findByUserId(user.getId()).stream().map(a->new AvailabilityResponse(a.getDay(),a.getStartTime(),a.getEndTime())).toList(); return new ProfileResponse(user.getId(),p.getDisplayName(),p.getMajor(),p.getInterestsCsv(),p.getStudyMode(),p.isOnboardingComplete(),courseDtos,times); }
  public MeResponse me(User user){ boolean complete=profiles.findById(user.getId()).map(StudentProfile::isOnboardingComplete).orElse(false); return new MeResponse(user.getId(),user.getEmail(),user.getRole().name(),complete); }
}
