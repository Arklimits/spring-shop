package com.arklimits.shop.member;

import com.arklimits.shop.member.dto.MemberDto;
import com.arklimits.shop.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/mypage")
    public String myPage(Authentication auth) {
        CustomUser principal = (CustomUser) auth.getPrincipal();
        System.out.println(auth.getPrincipal());
        System.out.println(principal.getDisplayName());
        return "mypage";
    }

    @PostMapping("/member")
    public String addMember(Member member) {
        memberService.addMember(member);
        return "redirect:list";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public MemberDto getUser(@PathVariable Long id) {
        return memberService.getUserById(id);
    }
}
