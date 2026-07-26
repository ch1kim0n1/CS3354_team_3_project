package com.studybuddy.api.repo;
import com.studybuddy.api.domain.AuditLog; import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditLogRepository extends JpaRepository<AuditLog,Long>{}
