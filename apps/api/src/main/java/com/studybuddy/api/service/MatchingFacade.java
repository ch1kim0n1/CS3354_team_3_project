package com.studybuddy.api.service;

import com.studybuddy.api.api.ApiDtos.MatchResponse;
import com.studybuddy.api.domain.*;
import com.studybuddy.api.repo.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchingFacade {
  private final ProfileRepository profiles; private final UserRepository users; private final EnrollmentRepository enrollments; private final AvailabilityRepository availability; private final MatchService matcher;
  public MatchingFacade(ProfileRepository profiles,UserRepository users,EnrollmentRepository enrollments,AvailabilityRepository availability,MatchService matcher){this.profiles=profiles;this.users=users;this.enrollments=enrollments;this.availability=availability;this.matcher=matcher;}
  @Transactional(readOnly=true) public List<MatchResponse> matches(User self){ MatchService.Candidate source=candidate(self); return users.findAll().stream().filter(u->!u.getId().equals(self.getId()) && u.getStatus()==AccountStatus.ACTIVE).map(this::candidate).map(c->matcher.score(source,c)).flatMap(Optional::stream).sorted(Comparator.comparingInt(MatchService.Scored::score).reversed()).map(s->new MatchResponse(s.candidate().userId(),s.candidate().displayName(),s.candidate().major(),sharedCourses(source,s.candidate()),s.candidate().studyMode(),s.score(),s.reasons())).toList(); }
  private MatchService.Candidate candidate(User user){ StudentProfile p=profiles.findById(user.getId()).orElseThrow(); Set<String> courseCodes=new TreeSet<>(); enrollments.findByUserIdAndActiveTrue(user.getId()).forEach(e->courseCodes.add(e.getCourse().getCode())); Set<String> interests=Arrays.stream(p.getInterestsCsv().toLowerCase().split(",")).map(String::trim).filter(s->!s.isBlank()).collect(java.util.stream.Collectors.toSet()); List<MatchService.Window> times=availability.findByUserId(user.getId()).stream().map(a->new MatchService.Window(a.getDay(),a.getStartTime(),a.getEndTime())).toList(); return new MatchService.Candidate(user.getId(),p.getDisplayName(),p.getMajor(),courseCodes,interests,p.getStudyMode(),times); }
  private List<String> sharedCourses(MatchService.Candidate a,MatchService.Candidate b){ Set<String> shared=new TreeSet<>(a.courses());shared.retainAll(b.courses());return List.copyOf(shared); }
}
