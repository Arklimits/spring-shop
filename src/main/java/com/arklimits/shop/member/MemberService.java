package com.arklimits.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void addMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberRepository.save(member);
    }
}
