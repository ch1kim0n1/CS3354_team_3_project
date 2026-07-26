package com.studybuddy.api.repo;
import com.studybuddy.api.domain.StudyGroup; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface GroupRepository extends JpaRepository<StudyGroup,Long>{ List<StudyGroup> findByCourseId(Long courseId); }
