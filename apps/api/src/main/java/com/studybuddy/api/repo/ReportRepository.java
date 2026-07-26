package com.studybuddy.api.repo;
import com.studybuddy.api.domain.Report; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface ReportRepository extends JpaRepository<Report,Long>{ List<Report> findByStatusOrderByIdAsc(com.studybuddy.api.domain.ReportStatus status); }
