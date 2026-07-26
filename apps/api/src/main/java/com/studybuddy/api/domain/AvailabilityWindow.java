package com.studybuddy.api.domain;
import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
@Entity @Table(name="availability_windows")
public class AvailabilityWindow { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false) private User user; @Enumerated(EnumType.STRING) @Column(nullable=false) private DayOfWeek day; @Column(name="start_time",nullable=false) private LocalTime startTime; @Column(name="end_time",nullable=false) private LocalTime endTime; protected AvailabilityWindow(){} public AvailabilityWindow(User user,DayOfWeek day,LocalTime start,LocalTime end){this.user=user;this.day=day;startTime=start;endTime=end;} public User getUser(){return user;} public DayOfWeek getDay(){return day;} public LocalTime getStartTime(){return startTime;} public LocalTime getEndTime(){return endTime;} }
