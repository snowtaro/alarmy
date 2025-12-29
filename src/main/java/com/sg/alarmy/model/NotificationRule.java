package com.sg.alarmy.model;

import com.sg.alarmy.persistence.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notification_rules")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false, length = 20)
    private RuleType ruleType; // IMMEDIATE, BEFORE_3_DAYS, ON_CHANGE

    // MySQL JSON 컬럼 매핑
    @Convert(converter = StringListConverter.class)
    @Column(name = "keywords", columnDefinition = "json")
    private List<String> keywords = new ArrayList<>();

    @Column(name = "is_active", nullable = false)
    private boolean active = true; // 필드명은 active로 두는 게 덜 헷갈림

    @Column(name = "last_triggered_at")
    private LocalDateTime lastTriggeredAt;

    // DB default 값 사용
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum RuleType {
        IMMEDIATE, BEFORE_3_DAYS, ON_CHANGE
    }

    public void deactivate() {
        this.active = false;
    }

    public void markTriggeredNow() {
        this.lastTriggeredAt = LocalDateTime.now();
    }
}
