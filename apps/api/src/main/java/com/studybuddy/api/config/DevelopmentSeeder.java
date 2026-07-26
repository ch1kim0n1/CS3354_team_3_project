package com.studybuddy.api.config;

import com.studybuddy.api.domain.*;
import com.studybuddy.api.repo.*;
import java.time.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@Profile("dev")
public class DevelopmentSeeder {
  @Bean CommandLineRunner seed(UserRepository users, ProfileRepository profiles, CourseRepository courses, EnrollmentRepository enrollments, AvailabilityRepository availability, PasswordEncoder encoder, PlatformTransactionManager transactionManager) {
    return args -> new TransactionTemplate(transactionManager).executeWithoutResult(ignored -> { if (users.count() > 0) return;
      Course cs3354=courses.save(new Course("CS 3354","Software Engineering")); Course cs3345=courses.save(new Course("CS 3345","Data Structures"));
      User ada=users.save(new User("ada@utdallas.edu",encoder.encode("StudyBuddy123!"),AccountRole.STUDENT)); User sam=users.save(new User("sam@utdallas.edu",encoder.encode("StudyBuddy123!"),AccountRole.STUDENT)); User admin=users.save(new User("admin@utdallas.edu",encoder.encode("StudyBuddy123!"),AccountRole.ADMIN));
      profiles.save(new StudentProfile(ada,"Ada Lovelace","Computer Science","algorithms,software design","IN_PERSON")); profiles.save(new StudentProfile(sam,"Sam Rivera","Computer Science","software design,exam prep","FLEXIBLE")); profiles.save(new StudentProfile(admin,"Study Buddy Admin","Administration","","FLEXIBLE"));
      enrollments.save(new CourseEnrollment(ada,cs3354,"Fall 2026")); enrollments.save(new CourseEnrollment(ada,cs3345,"Fall 2026")); enrollments.save(new CourseEnrollment(sam,cs3354,"Fall 2026"));
      availability.save(new AvailabilityWindow(ada,DayOfWeek.TUESDAY,LocalTime.of(18,0),LocalTime.of(20,0))); availability.save(new AvailabilityWindow(sam,DayOfWeek.TUESDAY,LocalTime.of(19,0),LocalTime.of(21,0)));
    });
  }
}
