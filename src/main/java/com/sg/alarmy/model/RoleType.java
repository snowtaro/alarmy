package com.sg.alarmy.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ROLE_USER("일반 사용자"),
    ROLE_ADMIN("운영 관리자");

    private final String description;
}