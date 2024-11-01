package com.arklimits.shop.domain.member.controller;

import com.arklimits.shop.domain.member.entity.Member;
import com.arklimits.shop.domain.member.security.CustomUser;
import com.arklimits.shop.domain.member.service.MemberService;
import com.arklimits.shop.domain.order.dto.OrderDTO;
import com.arklimits.shop.domain.order.service.OrderService;
import com.arklimits.shop.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/register")
    public String registerPage() {
        return "member/register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }

    @PostMapping("/login/jwt")
    public String loginJWT(@RequestBody Map<String, String> body, HttpServletRequest request,
        HttpServletResponse response) {
        var authToken = new UsernamePasswordAuthenticationToken(body.get("username"),
            body.get("password"));
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(jwt);

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(60 * 30);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 이전 페이지로 리다이렉트
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer
            : "/item/list"); // referer가 없을 경우 기본 경로로 이동
    }

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
