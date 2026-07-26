package com.studybuddy.api.repo;
import com.studybuddy.api.domain.User; import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Long>{ Optional<User> findByEmailIgnoreCase(String email); }
