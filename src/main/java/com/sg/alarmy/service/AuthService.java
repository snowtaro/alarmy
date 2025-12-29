package com.sg.alarmy.service;

import com.sg.alarmy.dto.LoginDto;
import com.sg.alarmy.model.Member;
import com.sg.alarmy.repository.MemberRepository;
import com.sg.alarmy.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public LoginDto.Response login(LoginDto.Request request) {
        // 1. 사용자 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 3. Member 엔티티의 권한 정보를 바탕으로 SimpleGrantedAuthority 리스트 생성
        List<SimpleGrantedAuthority> authorities = member.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthorityName().name()))
                .collect(Collectors.toList());

        // 4. Authentication 객체 생성 (JwtTokenProvider의 인자 타입에 맞춤)
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getEmail(),
                null,
                authorities
        );

        // 5. 토큰 생성
        String token = jwtTokenProvider.createToken(authentication);

        return new LoginDto.Response(token, member.getNickname());
    }
}

