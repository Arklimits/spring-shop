package com.arklimits.shop.domain.member.controller;

import com.arklimits.shop.domain.member.security.CustomUser;
import com.arklimits.shop.domain.order.dto.OrderDTO;
import com.arklimits.shop.domain.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final OrderService orderService;


    @GetMapping("/register")
    public String registerPage() {
        return "member/register";
    }

    @GetMapping("/mypage")
    public String myPage(Authentication auth, Model model) {
        CustomUser principal = (CustomUser) auth.getPrincipal();

        List<OrderDTO> result = orderService.findOrdersByMemberId(principal.getId());

        model.addAttribute("orders", result);

        return "member/myPage";
    }
}
