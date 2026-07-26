package com.studybuddy.api.repo;
import com.studybuddy.api.domain.*; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface EnrollmentRepository extends JpaRepository<CourseEnrollment,Long>{ List<CourseEnrollment> findByUserIdAndActiveTrue(Long userId); List<CourseEnrollment> findByCourseIdAndActiveTrue(Long courseId); boolean existsByUserIdAndCourseIdAndTerm(Long userId,Long courseId,String term); }
