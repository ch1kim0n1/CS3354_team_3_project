package com.studybuddy.api.repo;
import com.studybuddy.api.domain.StudySession; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface SessionRepository extends JpaRepository<StudySession,Long>{ List<StudySession> findByGroupIdOrderByStartsAtAsc(Long groupId); }
