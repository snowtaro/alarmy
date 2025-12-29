package com.sg.alarmy.repository;

import com.sg.alarmy.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    public Optional<Member> findByEmail(String email);
}
