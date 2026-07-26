package com.studybuddy.api.repo;
import com.studybuddy.api.domain.Course; import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository;
public interface CourseRepository extends JpaRepository<Course,Long>{Optional<Course> findByCodeIgnoreCase(String code);}
