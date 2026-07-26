package com.studybuddy.api.match;

import static org.assertj.core.api.Assertions.assertThat;
import com.studybuddy.api.service.MatchService;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.Test;

class MatchServiceTest {
  private final MatchService service = new MatchService();
  @Test void scoresStudentsWhoShareAnActiveCourseAndAvailability() {
    var ada = candidate(1L,Set.of("CS 3354"),Set.of("software design"),"IN_PERSON",18,20);
    var sam = candidate(2L,Set.of("CS 3354"),Set.of("software design"),"FLEXIBLE",19,21);
    var result = service.score(ada,sam);
    assertThat(result).isPresent(); assertThat(result.orElseThrow().score()).isGreaterThanOrEqualTo(70); assertThat(result.orElseThrow().reasons()).contains("1 shared course","overlapping availability");
  }
  @Test void doesNotMatchStudentsWithoutASharedCourse() {
    var ada = candidate(1L,Set.of("CS 3354"),Set.of("software design"),"IN_PERSON",18,20);
    var lee = candidate(3L,Set.of("CS 2305"),Set.of("history"),"ONLINE",18,20);
    assertThat(service.score(ada,lee)).isEmpty();
  }
  private MatchService.Candidate candidate(Long id,Set<String> courses,Set<String> interests,String mode,int start,int end){return new MatchService.Candidate(id,"Student","CS",courses,interests,mode,List.of(new MatchService.Window(DayOfWeek.TUESDAY,LocalTime.of(start,0),LocalTime.of(end,0))));}
}
