package com.sg.alarmy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcement_snapshots")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @Lob
    @Column(name = "raw_content", columnDefinition = "TEXT")
    private String rawContent;

    @Column(name = "content_hash", nullable = false, length = 64)
    private String contentHash;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public AnnouncementSnapshot(Announcement announcement, String rawContent, String contentHash, Integer version, LocalDateTime createdAt) {
        this.announcement = announcement;
        this.contentHash = contentHash;
        this.rawContent = rawContent;
        this.version = version;
        this.createdAt = createdAt;
    }
}
