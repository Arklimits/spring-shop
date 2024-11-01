package com.arklimits.shop.controller;

import com.arklimits.shop.domain.post.entity.Post;
import com.arklimits.shop.domain.post.service.PostService;
import com.arklimits.shop.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PostService postService;

    @GetMapping("/")
    String home(Model model) {

        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "main/index";
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
}
