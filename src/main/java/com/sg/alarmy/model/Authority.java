package com.sg.alarmy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING) // DB에 문자열("ROLE_USER")로 저장되도록 설정
    @Column(name = "authority_name", nullable = false)
    private RoleType authorityName;

    @Builder
    public Authority(Member member, RoleType authorityName) {
        this.member = member;
        this.authorityName = authorityName;
    }
}
