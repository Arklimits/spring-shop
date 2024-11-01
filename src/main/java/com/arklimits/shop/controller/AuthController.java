package com.arklimits.shop.controller;

import com.arklimits.shop.domain.member.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/jwt")
    String myPageJWT(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        System.out.println(user);
        System.out.println(user.getAuthorities());

        return "방구뿡";
    }
}
