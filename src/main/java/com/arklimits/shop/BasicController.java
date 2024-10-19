package com.arklimits.shop;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/")
    String home() {
        return "index";
    }

    @GetMapping("/about")
    @ResponseBody
    String about() {
        return "Site for practice Spring Boot";
    }

    @GetMapping("/date")
    @ResponseBody
    LocalDateTime date() {
        return LocalDateTime.now();
    }

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> body, HttpServletResponse response) {
        var authToken = new UsernamePasswordAuthenticationToken(body.get("username"),
            body.get("password"));
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(jwt);

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(10);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return jwt;
    }
}
