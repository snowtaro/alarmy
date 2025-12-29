-- 1. 회원 및 권한 관련
CREATE TABLE members (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         nickname VARCHAR(50) NOT NULL,
                         is_active BOOLEAN DEFAULT TRUE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 권한 테이블 (ROLE_USER, ROLE_ADMIN 관리)
CREATE TABLE authorities (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             member_id BIGINT NOT NULL,
                             authority_name VARCHAR(50) NOT NULL, -- 'ROLE_USER', 'ROLE_ADMIN'
                             FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
                             UNIQUE KEY (member_id, authority_name) -- 한 유저가 동일 권한 중복 방지
);

-- 2. 공고 관련
CREATE TABLE announcements (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               external_id VARCHAR(100) UNIQUE NOT NULL, -- 크롤링 원본 ID
                               url VARCHAR(500) NOT NULL,
                               title VARCHAR(255) NOT NULL,
                               category VARCHAR(50),
                               posted_at DATETIME NULL,
                               modified_at DATETIME NULL,
                               deadline_at DATETIME NULL,
                               view_count INT DEFAULT 0,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               INDEX idx_modified_at (modified_at),
                               INDEX idx_deadline_at (deadline_at),
                               INDEX idx_posted_at (posted_at)
);

CREATE TABLE announcement_snapshots (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        announcement_id BIGINT NOT NULL,
                                        content_hash VARCHAR(64) NOT NULL,
                                        raw_content TEXT,
                                        version INT NOT NULL,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        FOREIGN KEY (announcement_id) REFERENCES announcements(id) ON DELETE CASCADE
);

-- 3. 알림 설정 및 이력
CREATE TABLE notification_rules (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    member_id BIGINT NOT NULL,
                                    rule_type ENUM('IMMEDIATE', 'BEFORE_3_DAYS', 'ON_CHANGE') NOT NULL,
                                    keywords JSON, -- ["Java", "Backend"] 형태의 필터링 키워드
                                    is_active BOOLEAN DEFAULT TRUE,
                                    last_triggered_at DATETIME,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
);

CREATE TABLE notification_histories (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        member_id BIGINT NOT NULL,
                                        announcement_id BIGINT NOT NULL,
                                        snapshot_id BIGINT,
                                        sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        status ENUM('SUCCESS', 'FAILED') DEFAULT 'SUCCESS',
                                        error_message TEXT,
                                        UNIQUE KEY (member_id, announcement_id, snapshot_id),
                                        FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
                                        FOREIGN KEY (announcement_id) REFERENCES announcements(id) ON DELETE CASCADE
);