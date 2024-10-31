package com.arklimits.shop.domain.member.controller;

import com.arklimits.shop.domain.member.dto.MemberDTO;
import com.arklimits.shop.domain.member.security.CustomUser;
import com.arklimits.shop.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public MemberDTO getUser(@PathVariable Long id) {
        return memberService.getUserById(id);
    }

    @GetMapping("/jwt")
    String myPageJWT(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        System.out.println(user);
        System.out.println(user.getAuthorities());

        return "방구뿡";
    }
}
