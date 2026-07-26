package com.studybuddy.api.domain;
import jakarta.persistence.*;
@Entity @Table(name="courses", uniqueConstraints=@UniqueConstraint(columnNames="code"))
public class Course { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false,length=32) private String code; @Column(nullable=false,length=160) private String name; protected Course(){} public Course(String code,String name){this.code=code.toUpperCase();this.name=name;} public Long getId(){return id;} public String getCode(){return code;} public String getName(){return name;} }
