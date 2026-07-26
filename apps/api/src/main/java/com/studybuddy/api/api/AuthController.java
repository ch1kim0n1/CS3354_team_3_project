package com.studybuddy.api.api;

import com.studybuddy.api.api.ApiDtos.*;
import com.studybuddy.api.domain.User;
import com.studybuddy.api.security.CurrentUser;
import com.studybuddy.api.service.AccountService;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/auth")
public class AuthController {
  private final AccountService accounts; private final AuthenticationManager authenticationManager; private final CurrentUser currentUser;
  public AuthController(AccountService accounts,AuthenticationConfiguration configuration,CurrentUser currentUser) throws Exception{this.accounts=accounts;this.authenticationManager=configuration.getAuthenticationManager();this.currentUser=currentUser;}
  @GetMapping("/csrf") public Map<String,String> csrf(CsrfToken token){return Map.of("token",token.getToken(),"headerName",token.getHeaderName());}
  @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public MeResponse register(@Valid @RequestBody RegisterRequest request){User user=accounts.register(request);return accounts.me(user);}
  @PostMapping("/login") public MeResponse login(@Valid @RequestBody LoginRequest request,HttpServletRequest httpRequest){Authentication authentication=authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(request.email().toLowerCase(),request.password()));SecurityContext context=SecurityContextHolder.createEmptyContext();context.setAuthentication(authentication);SecurityContextHolder.setContext(context);httpRequest.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,context);return accounts.me(currentUser.require(authentication));}
  @PostMapping("/logout") @ResponseStatus(HttpStatus.NO_CONTENT) public void logout(HttpServletRequest request){HttpSession session=request.getSession(false);if(session!=null)session.invalidate();SecurityContextHolder.clearContext();}
  @GetMapping("/me") public MeResponse me(Authentication authentication){return accounts.me(currentUser.require(authentication));}
}
