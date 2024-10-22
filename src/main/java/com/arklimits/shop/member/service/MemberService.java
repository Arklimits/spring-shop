package com.arklimits.shop.member.service;

import com.arklimits.shop.member.dto.MemberDTO;
import com.arklimits.shop.member.entity.Member;
import com.arklimits.shop.member.repository.MemberRepository;
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

    public MemberDTO getUserById(Long id) {
        var member = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        return new MemberDTO(member.getId(), member.getUsername(),
            member.getDisplayName());
    }
}
