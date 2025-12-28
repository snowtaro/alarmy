package com.sg.alarmy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "notification_histories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_announcement_snapshot",
                        columnNames = {"user_id", "announcement_id", "snapshot_id"}
                )
        }
)
public class NotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "announcement_id", nullable = false)
    private Long announcementId;

    @Column(name = "snapshot_id")
    private Long snapshotId; // 변경 감지 알림일 경우 스냅샷 ID 포함

    @Enumerated(EnumType.STRING)
    private NotificationStatus status; // SUCCESS, FAILED

    private String message; // 발송된 알림 메시지 요약

    private LocalDateTime sentAt;

    @Builder
    public NotificationHistory(Long userId, Long announcementId, Long snapshotId, NotificationStatus status, String message) {
        this.userId = userId;
        this.announcementId = announcementId;
        this.snapshotId = snapshotId;
        this.status = status;
        this.message = message;
        this.sentAt = LocalDateTime.now();
    }

    public enum NotificationStatus {
        SUCCESS, FAILED
    }
}
