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
                        columnNames = {"member_id", "announcement_id", "snapshot_id"} // user_id -> member_id
                )
        }
)
public class NotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false) // SQL 기준 수정
    private Long memberId;

    @Column(name = "announcement_id", nullable = false)
    private Long announcementId;

    @Column(name = "snapshot_id")
    private Long snapshotId;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(name = "error_message", columnDefinition = "TEXT") // message -> error_message
    private String errorMessage;

    @Column(name = "sent_at", insertable = false, updatable = false) // SQL default CURRENT_TIMESTAMP 반영
    private LocalDateTime sentAt;

    @Builder
    public NotificationHistory(Long memberId, Long announcementId, Long snapshotId, NotificationStatus status, String errorMessage) {
        this.memberId = memberId;
        this.announcementId = announcementId;
        this.snapshotId = snapshotId;
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
