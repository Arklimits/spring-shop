package com.arklimits.shop.domain.member.controller;

import com.arklimits.shop.domain.member.entity.Member;
import com.arklimits.shop.domain.member.security.CustomUser;
import com.arklimits.shop.domain.member.service.MemberService;
import com.arklimits.shop.domain.order.dto.OrderDTO;
import com.arklimits.shop.domain.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/mypage")
    public String myPage(Authentication auth, Model model) {
        CustomUser principal = (CustomUser) auth.getPrincipal();
        System.out.println(auth.getPrincipal());
        System.out.println(principal.getDisplayName());

        List<OrderDTO> result = orderService.findAll();

        model.addAttribute("orders", result);

        return "member/mypage";
    }

    @PostMapping("/member")
    public String addMember(Member member) {
        memberService.addMember(member);
        return "redirect:item/list";
    }
}
