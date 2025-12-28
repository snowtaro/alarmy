package com.sg.alarmy.model;

import com.sg.alarmy.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private RuleType ruleType; // IMMEDIATE, BEFORE_3_DAYS, ON_CHANGE

    @Convert(converter = StringListConverter.class) // JSON 또는 콤마 구분자 저장
    private List<String> keywords = new ArrayList<>();

    private boolean isActive = true;

    public enum RuleType {
        IMMEDIATE, BEFORE_3_DAYS, ON_CHANGE
    }
}
