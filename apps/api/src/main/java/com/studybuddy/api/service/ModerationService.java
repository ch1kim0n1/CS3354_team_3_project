package com.studybuddy.api.service;

import com.studybuddy.api.api.ApiDtos.*;
import com.studybuddy.api.domain.*;
import com.studybuddy.api.repo.*;
import java.util.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModerationService {
  private final ReportRepository reports; private final UserRepository users; private final MessageRepository messages; private final AuditLogRepository audit;
  public ModerationService(ReportRepository reports,UserRepository users,MessageRepository messages,AuditLogRepository audit){this.reports=reports;this.users=users;this.messages=messages;this.audit=audit;}
  @Transactional public ReportResponse report(User reporter,ReportRequest request){ Report r=reports.save(new Report(reporter,request.targetType(),request.targetId(),request.reason()));audit.save(new AuditLog(reporter,"REPORT_CREATED",request.targetType(),request.targetId()));return dto(r); }
  @Transactional(readOnly=true) public List<ReportResponse> openReports(){return reports.findByStatusOrderByIdAsc(ReportStatus.OPEN).stream().map(this::dto).toList();}
  @Transactional public void deactivate(User admin,Long userId,boolean active){User target=users.findById(userId).orElseThrow(()->new NoSuchElementException("User not found."));target.setStatus(active?AccountStatus.ACTIVE:AccountStatus.DEACTIVATED);audit.save(new AuditLog(admin,active?"ACCOUNT_REACTIVATED":"ACCOUNT_DEACTIVATED","USER",userId));}
  @Transactional public void removeMessage(User admin,Long messageId){GroupMessage message=messages.findById(messageId).orElseThrow(()->new NoSuchElementException("Message not found."));message.remove();audit.save(new AuditLog(admin,"MESSAGE_REMOVED","MESSAGE",messageId));}
  private ReportResponse dto(Report r){return new ReportResponse(r.getId(),r.getTargetType(),r.getTargetId(),r.getReason(),r.getStatus().name());}
}
