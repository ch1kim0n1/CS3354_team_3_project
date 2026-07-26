package com.studybuddy.api.repo;
import com.studybuddy.api.domain.*; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface MembershipRepository extends JpaRepository<GroupMembership,Long>{ Optional<GroupMembership> findByGroupIdAndUserId(Long groupId,Long userId); List<GroupMembership> findByGroupId(Long groupId); long countByGroupIdAndStatus(Long groupId,MembershipStatus status); }
