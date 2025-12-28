-- 공고 테이블
CREATE TABLE announcements (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               external_id VARCHAR(100) UNIQUE NOT NULL, -- 크롤링 원본 ID
                               title VARCHAR(255) NOT NULL,
                               category VARCHAR(50),
                               deadline_at DATETIME,
                               view_count INT DEFAULT 0,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 공고 스냅샷 (버전 관리 및 Diff용)
CREATE TABLE announcement_snapshots (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        announcement_id BIGINT NOT NULL,
                                        content_hash VARCHAR(64) NOT NULL, -- Redis 중복 체크용과 동일한 해시
                                        raw_content TEXT,
                                        version INT NOT NULL,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        FOREIGN KEY (announcement_id) REFERENCES announcements(id)
);

-- 사용자 알림 설정 (QueryDSL 매칭 타겟)
CREATE TABLE notification_rules (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    user_id BIGINT NOT NULL,
                                    rule_type ENUM('IMMEDIATE', 'BEFORE_3_DAYS', 'ON_CHANGE') NOT NULL,
                                    keywords JSON, -- 개인화 필터 (예: ["Java", "Backend"])
                                    is_active BOOLEAN DEFAULT TRUE,
                                    last_triggered_at DATETIME,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 알림 발송 이력 (신뢰성 보장용)
CREATE TABLE notification_histories (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        announcement_id BIGINT NOT NULL,
                                        snapshot_id BIGINT,
                                        sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        status ENUM('SUCCESS', 'FAILED') DEFAULT 'SUCCESS',
                                        UNIQUE KEY (user_id, announcement_id, snapshot_id) -- 원천적 중복 방지
);