package com.studybuddy.api.service;

import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
  public record Window(DayOfWeek day, LocalTime start, LocalTime end) {}
  public record Candidate(Long userId, String displayName, String major, Set<String> courses, Set<String> interests, String studyMode, List<Window> availability) {}
  public record Scored(Candidate candidate, int score, List<String> reasons) {}
  public Optional<Scored> score(Candidate self, Candidate candidate) {
    Set<String> shared = new TreeSet<>(self.courses()); shared.retainAll(candidate.courses());
    if (shared.isEmpty()) return Optional.empty();
    Set<String> commonInterests = new TreeSet<>(self.interests()); commonInterests.retainAll(candidate.interests());
    boolean compatibleMode = "FLEXIBLE".equalsIgnoreCase(self.studyMode()) || "FLEXIBLE".equalsIgnoreCase(candidate.studyMode()) || self.studyMode().equalsIgnoreCase(candidate.studyMode());
    boolean overlap = hasOverlap(self.availability(), candidate.availability());
    int score = shared.size() * 50 + commonInterests.size() * 10 + (compatibleMode ? 10 : 0) + (overlap ? 10 : 0);
    List<String> reasons = new ArrayList<>(); reasons.add(shared.size() + " shared course" + (shared.size() == 1 ? "" : "s"));
    if (!commonInterests.isEmpty()) reasons.add("shared academic interests"); if (compatibleMode) reasons.add("compatible study mode"); if (overlap) reasons.add("overlapping availability");
    return Optional.of(new Scored(candidate, score, reasons));
  }
  private boolean hasOverlap(List<Window> left, List<Window> right) { return left.stream().anyMatch(a -> right.stream().anyMatch(b -> a.day()==b.day() && a.start().isBefore(b.end()) && b.start().isBefore(a.end()))); }
}
