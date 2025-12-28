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
public class AnnouncementSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String contentHash; // Redis 중복 체크용 (SHA-256 등)

    private Integer version;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public AnnouncementSnapshot(Announcement announcement, String content, String contentHash, Integer version) {
        this.announcement = announcement;
        this.content = content;
        this.contentHash = contentHash;
        this.version = version;
    }
}
