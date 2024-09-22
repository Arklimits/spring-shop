package com.arklimits.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    String registerPage() {
        return "register";
    }

    @PostMapping("/member")
    String addMember(Member member) {
        memberService.addMember(member);
        return "list";
    }
}
