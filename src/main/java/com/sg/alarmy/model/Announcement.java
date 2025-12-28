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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String externalId; // 크롤링 원본 사이트 ID

    private String title;

    private LocalDateTime deadlineAt;

    private int viewCount = 0;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL)
    private List<AnnouncementSnapshot> snapshots = new ArrayList<>();

    @Builder
    public Announcement(String externalId, String title, LocalDateTime deadlineAt) {
        this.externalId = externalId;
        this.title = title;
        this.deadlineAt = deadlineAt;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
