package com.arklimits.shop.domain.member.service;

import com.arklimits.shop.domain.member.dto.MemberDTO;
import com.arklimits.shop.domain.member.dto.RegisterMemberDTO;
import com.arklimits.shop.domain.member.entity.Member;
import com.arklimits.shop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isUsernameDuplicate(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }

    public void addMember(RegisterMemberDTO registerMemberDTO) {

        Member member = Member.builder()
            .username(registerMemberDTO.getUsername())
            .password(passwordEncoder.encode(registerMemberDTO.getPassword()))
            .displayName(registerMemberDTO.getDisplayName())
            .build();

        memberRepository.save(member);
    }

    public MemberDTO getUserById(Long id) {
        var member = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        return new MemberDTO(member.getId(), member.getUsername(),
            member.getDisplayName());
    }
}
