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

    // SQL 스키마의 member_id와 일치하도록 수정
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false, length = 20)
    private RuleType ruleType;

    @Convert(converter = StringListConverter.class)
    @Column(name = "keywords", columnDefinition = "json")
    private List<String> keywords = new ArrayList<>();

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(name = "last_triggered_at")
    private LocalDateTime lastTriggeredAt;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public void deactivate() {
        this.active = false;
    }

    public void markTriggeredNow() {
        this.lastTriggeredAt = LocalDateTime.now();
    }
}
