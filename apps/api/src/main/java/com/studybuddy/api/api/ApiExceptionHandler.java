package com.studybuddy.api.api;

import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
  ResponseEntity<Map<String,Object>> badRequest(Exception ex){ return error(HttpStatus.BAD_REQUEST,ex.getMessage()); }
  @ExceptionHandler(AccessDeniedException.class)
  ResponseEntity<Map<String,Object>> forbidden(AccessDeniedException ex){ return error(HttpStatus.FORBIDDEN,"You do not have permission to perform this action."); }
  @ExceptionHandler(java.util.NoSuchElementException.class)
  ResponseEntity<Map<String,Object>> notFound(java.util.NoSuchElementException ex){ return error(HttpStatus.NOT_FOUND,ex.getMessage()); }
  private ResponseEntity<Map<String,Object>> error(HttpStatus status,String message){ return ResponseEntity.status(status).body(Map.of("timestamp", Instant.now().toString(),"status",status.value(),"message",message == null ? "Request failed" : message)); }
}
