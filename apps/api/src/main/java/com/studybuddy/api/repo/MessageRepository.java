package com.studybuddy.api.repo;
import com.studybuddy.api.domain.GroupMessage; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface MessageRepository extends JpaRepository<GroupMessage,Long>{ List<GroupMessage> findByGroupIdOrderByCreatedAtAsc(Long groupId); }
