package com.sg.alarmy.dto.request;

import com.sg.alarmy.model.Announcement;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AnnouncementDto {
    private Long id;
    private String title;
    private String category;
    private String url;
    private LocalDateTime deadlineAt;
    private int viewCount;

    public AnnouncementDto(Announcement entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.url = entity.getUrl();
        this.deadlineAt = entity.getDeadlineAt();
        this.viewCount = entity.getViewCount();
    }
}
