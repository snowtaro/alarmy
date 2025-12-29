package com.sg.alarmy.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(name = "is_active")
    private boolean isActive = true;

    // 1:N 권한 설정 (Member가 삭제되면 권한도 삭제되도록 Cascade 설정)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authority> authorities = new ArrayList<>();

    @Builder
    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isActive = true;
    }

    // 권한 추가용
    public void addAuthority(RoleType roleType) {
        Authority authority = Authority.builder()
                .member(this)
                .authorityName(roleType)
                .build();
        this.authorities.add(authority);
    }

    // 정보 수정용
    public void updateProfile(String nickname) {
        this.nickname = nickname;
    }
}
