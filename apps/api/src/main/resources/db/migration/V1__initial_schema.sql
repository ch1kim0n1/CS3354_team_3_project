CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(254) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL
);
CREATE TABLE student_profiles (
  user_id BIGINT PRIMARY KEY,
  display_name VARCHAR(80) NOT NULL,
  major VARCHAR(120),
  interests_csv VARCHAR(500),
  study_mode VARCHAR(40),
  onboarding_complete BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_profile_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE courses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(32) NOT NULL UNIQUE,
  name VARCHAR(160) NOT NULL
);
CREATE TABLE course_enrollments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  term VARCHAR(32) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT uq_enrollment UNIQUE(user_id, course_id, term),
  CONSTRAINT fk_enrollment_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_enrollment_course FOREIGN KEY (course_id) REFERENCES courses(id)
);
CREATE TABLE availability_windows (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  day VARCHAR(12) NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  CONSTRAINT fk_availability_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT ck_availability_range CHECK (end_time > start_time)
);
CREATE TABLE study_groups (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  course_id BIGINT NOT NULL,
  owner_id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(1000) NOT NULL,
  capacity INT NOT NULL,
  study_mode VARCHAR(40) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL,
  CONSTRAINT fk_group_course FOREIGN KEY (course_id) REFERENCES courses(id),
  CONSTRAINT fk_group_owner FOREIGN KEY (owner_id) REFERENCES users(id),
  CONSTRAINT ck_group_capacity CHECK (capacity BETWEEN 2 AND 100)
);
CREATE TABLE group_memberships (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  group_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  status VARCHAR(20) NOT NULL,
  is_coordinator BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP(6) NOT NULL,
  CONSTRAINT uq_group_member UNIQUE(group_id, user_id),
  CONSTRAINT fk_membership_group FOREIGN KEY (group_id) REFERENCES study_groups(id),
  CONSTRAINT fk_membership_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE study_sessions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  group_id BIGINT NOT NULL,
  created_by BIGINT NOT NULL,
  starts_at TIMESTAMP(6) NOT NULL,
  ends_at TIMESTAMP(6) NOT NULL,
  location VARCHAR(300),
  virtual_url VARCHAR(500),
  agenda VARCHAR(1000),
  CONSTRAINT fk_session_group FOREIGN KEY (group_id) REFERENCES study_groups(id),
  CONSTRAINT fk_session_creator FOREIGN KEY (created_by) REFERENCES users(id),
  CONSTRAINT ck_session_range CHECK (ends_at > starts_at)
);
CREATE TABLE group_messages (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  group_id BIGINT NOT NULL,
  author_id BIGINT NOT NULL,
  body VARCHAR(2000) NOT NULL,
  removed_at TIMESTAMP(6),
  created_at TIMESTAMP(6) NOT NULL,
  CONSTRAINT fk_message_group FOREIGN KEY (group_id) REFERENCES study_groups(id),
  CONSTRAINT fk_message_author FOREIGN KEY (author_id) REFERENCES users(id)
);
CREATE TABLE reports (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  reporter_id BIGINT NOT NULL,
  target_type VARCHAR(32) NOT NULL,
  target_id BIGINT NOT NULL,
  reason VARCHAR(1000) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL,
  CONSTRAINT fk_reporter FOREIGN KEY (reporter_id) REFERENCES users(id)
);
CREATE TABLE audit_logs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  actor_id BIGINT,
  action VARCHAR(80) NOT NULL,
  target_type VARCHAR(32),
  target_id BIGINT,
  created_at TIMESTAMP(6) NOT NULL,
  CONSTRAINT fk_audit_actor FOREIGN KEY (actor_id) REFERENCES users(id)
);
CREATE INDEX idx_enrollment_course ON course_enrollments(course_id, active);
CREATE INDEX idx_membership_group ON group_memberships(group_id, status);
CREATE INDEX idx_message_group ON group_messages(group_id, created_at);
