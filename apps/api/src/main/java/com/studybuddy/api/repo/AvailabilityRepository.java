package com.studybuddy.api.repo;
import com.studybuddy.api.domain.AvailabilityWindow; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface AvailabilityRepository extends JpaRepository<AvailabilityWindow,Long>{ List<AvailabilityWindow> findByUserId(Long userId); void deleteByUserId(Long userId); }
