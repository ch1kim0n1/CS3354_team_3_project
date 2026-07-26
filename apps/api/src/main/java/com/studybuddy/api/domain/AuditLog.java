package com.studybuddy.api.domain;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="audit_logs")
public class AuditLog { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="actor_id") private User actor; @Column(nullable=false,length=80) private String action; @Column(name="target_type",length=32) private String targetType; @Column(name="target_id") private Long targetId; @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now(); protected AuditLog(){} public AuditLog(User actor,String action,String targetType,Long targetId){this.actor=actor;this.action=action;this.targetType=targetType;this.targetId=targetId;} public Long getId(){return id;} }
