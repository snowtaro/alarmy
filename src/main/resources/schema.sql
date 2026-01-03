-- 1. 회원 관련 (User.java 모델 반영)
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,          -- User.java의 username 필드
                       email VARCHAR(100) NOT NULL UNIQUE,             -- User.java의 email 필드
                       password VARCHAR(255) NOT NULL,
                       verification_code VARCHAR(255),                 -- 이메일 인증 코드
                       verification_expiration DATETIME,               -- 인증 코드 만료 시간
                       enabled BOOLEAN DEFAULT FALSE,                  -- User.java의 enabled 필드
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 권한 관련 (RoleType.java 반영 및 테이블 구조 간소화)
-- 엔티티 모델에는 별도의 Authority 객체가 없으나, 시큐리티 연동을 위해 유지
CREATE TABLE authorities (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             authority_name VARCHAR(20) NOT NULL, -- ROLE_USER, ROLE_ADMIN
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             UNIQUE KEY uk_user_authority (user_id, authority_name)
);

-- 2. 공고 관련 (Announcement.java & AnnouncementSnapshot.java 반영)
CREATE TABLE announcements (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               external_id VARCHAR(100) NOT NULL UNIQUE,
                               url VARCHAR(500) NOT NULL,
                               title VARCHAR(255) NOT NULL,
                               category VARCHAR(50),
                               posted_at DATETIME,
                               modified_at DATETIME,
                               deadline_at DATETIME,
                               view_count INT DEFAULT 0 NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               INDEX idx_modified_at (modified_at),
                               INDEX idx_deadline_at (deadline_at),
                               INDEX idx_posted_at (posted_at)
);

CREATE TABLE announcement_snapshots (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        announcement_id BIGINT NOT NULL,
                                        raw_content TEXT,                    -- @Lob 반영
                                        content_hash VARCHAR(64) NOT NULL,
                                        version INT NOT NULL,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        FOREIGN KEY (announcement_id) REFERENCES announcements(id) ON DELETE CASCADE
);

-- 3. 알림 설정 및 이력 (NotificationRule.java & NotificationHistory.java 반영)
CREATE TABLE notification_rules (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    user_id BIGINT NOT NULL,             -- 모델의 userId 필드와 매칭
                                    rule_type VARCHAR(20) NOT NULL,      -- IMMEDIATE, BEFORE_3_DAYS, ON_CHANGE
                                    keywords JSON,                       -- StringListConverter를 통한 JSON 저장
                                    is_active BOOLEAN DEFAULT TRUE NOT NULL,
                                    last_triggered_at DATETIME,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notification_histories (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_id BIGINT NOT NULL,             -- 모델의 userId 필드와 매칭
                                        announcement_id BIGINT NOT NULL,
                                        snapshot_id BIGINT,
                                        status VARCHAR(10),                  -- SUCCESS, FAILED (NotificationStatus enum)
                                        error_message TEXT,                  -- message -> error_message 반영
                                        sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        CONSTRAINT uk_user_announcement_snapshot UNIQUE (user_id, announcement_id, snapshot_id),
                                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                        FOREIGN KEY (announcement_id) REFERENCES announcements(id) ON DELETE CASCADE
);