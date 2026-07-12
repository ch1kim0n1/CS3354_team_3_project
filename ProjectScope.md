# Project Scope: Study Buddy Finder

**Course:** CS 3354 Software Engineering  
**Team:** Group 3  
**Version:** 1.0  
**Date:** July 12, 2026

## 1. Project Purpose

Study Buddy Finder is a web-based software application that helps university students connect with compatible study partners and form study groups. Students often prepare for classes, exams, and assignments alone even when classmates with similar courses and goals are available. The application will make it easier to find those classmates, organize a group, and schedule a session.

## 2. Project Objectives

The project will provide a secure, easy-to-use system that enables students to:

- create an account and maintain a study profile;
- identify potential study partners using shared courses, academic interests, and study preferences;
- create, join, and leave study groups; and
- schedule in-person or virtual study sessions.

Administrators will be able to manage user accounts and address inappropriate content or inactive accounts. The completed system will support productive academic collaboration while protecting user privacy.

## 3. In Scope

The first release of Study Buddy Finder includes the following capabilities:

| Area | Included functionality |
| --- | --- |
| Account access | Student registration, authentication, login, logout, and password protection. |
| Student profiles | Create and edit a profile containing major, courses, academic interests, and study preferences. |
| Partner discovery | Search for students by shared course or academic interest and view compatible profiles. |
| Study groups | Create, join, leave, and view study groups. |
| Study sessions | Create sessions with a date, time, and either a physical location or virtual meeting option. |
| Administration | Manage user accounts and remove inappropriate content or inactive accounts. |
| Data management | Store user, course, group, session, and relevant activity data in a MySQL database. |

## 4. Out of Scope

To keep the project achievable within the course timeline, the following are not included in the initial release:

- verification of course enrollment through a university database;
- integration with university single sign-on, calendars, learning-management systems, or video-conferencing services;
- payment processing, tutoring services, grade tracking, or academic advising;
- native iOS or Android applications;
- automated matching that uses grades, private academic records, or machine-learning recommendations; and
- responsibility for arranging transportation, room reservations, or supervising study sessions.

## 5. Users and Stakeholders

- **Students:** Register, manage profiles, search for peers, participate in study groups, and schedule sessions.
- **Administrators:** Manage accounts and moderate inappropriate content or inactive accounts.
- **Project team:** Designs, develops, tests, documents, and maintains the course project.
- **Course instructor and TA:** Review deliverables and evaluate the project.

## 6. Constraints and Assumptions

The system will be developed using Java, MySQL, and GitHub for version control. It will be available through modern web browsers and mobile devices with an internet connection. Users must register before accessing the application, provide accurate profile information, and follow university conduct policies. The system assumes users are university students, but it will not independently verify enrollment. Students may join multiple groups, and sessions may be in person or online.

## 7. Quality Boundaries

The application will aim to load pages and search results within three seconds under normal operating conditions, support at least 10,000 user profiles and study groups, and maintain 99% availability during the academic semester. Passwords will be encrypted, access will require authentication, and user information will not be disclosed without consent. The application will follow applicable university policies and data-privacy requirements.

## 8. Scope Acceptance Criteria

The project scope is considered met when a student can register, log in, create a profile, find peers through shared courses or interests, join or create a study group, and schedule a study session. An administrator must be able to manage accounts and moderate inappropriate content. All features must operate through the supported web interface and store their required information in the project database.
