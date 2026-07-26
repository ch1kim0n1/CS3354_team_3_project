package com.studybuddy.api.repo;
import com.studybuddy.api.domain.StudentProfile; import org.springframework.data.jpa.repository.JpaRepository;
public interface ProfileRepository extends JpaRepository<StudentProfile,Long>{}
