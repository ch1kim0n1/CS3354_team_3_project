package com.studybuddy.api.match;
import static org.assertj.core.api.Assertions.assertThat;
import com.studybuddy.api.security.CurrentUser; import com.studybuddy.api.repo.UserRepository; import org.junit.jupiter.api.Test; import org.springframework.security.access.AccessDeniedException;
class MatchAuthorizationTest { @Test void rejectsUnauthenticatedPrivateDataAccess(){ CurrentUser current=new CurrentUser(org.mockito.Mockito.mock(UserRepository.class)); org.junit.jupiter.api.Assertions.assertThrows(AccessDeniedException.class,()->current.require(null)); } }
