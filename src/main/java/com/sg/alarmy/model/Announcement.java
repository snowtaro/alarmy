package com.sg.alarmy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "announcements",
        indexes = {
                @Index(name = "idx_modified_at", columnList = "modified_at"),
                @Index(name = "idx_deadline_at", columnList = "deadline_at"),
                @Index(name = "idx_posted_at", columnList = "posted_at") // 추가된 인덱스
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true, length = 100)
    private String externalId; // 크롤링 원본 사이트 ID

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "deadline_at")
    private LocalDateTime deadlineAt;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnnouncementSnapshot> snapshots = new ArrayList<>();

    @Builder
    public Announcement(
            String externalId,
            String url,
            String title,
            String category,
            LocalDateTime postedAt,
            LocalDateTime modifiedAt,
            LocalDateTime deadlineAt
    ) {
        this.externalId = externalId;
        this.url = url;
        this.title = title;
        this.category = category;
        this.postedAt = postedAt;
        this.modifiedAt = modifiedAt;
        this.deadlineAt = deadlineAt;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void updateFromCrawl(
            String url,
            String title,
            String category,
            LocalDateTime postedAt,
            LocalDateTime modifiedAt,
            LocalDateTime deadlineAt
    ) {
        this.url = url;
        this.title = title;
        this.category = category;
        this.postedAt = postedAt;
        this.modifiedAt = modifiedAt;
        this.deadlineAt = deadlineAt;
    }
}
