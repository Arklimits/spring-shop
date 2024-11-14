package com.arklimits.shop.domain.auth.controller;

import com.arklimits.shop.domain.auth.dto.LoginRequestDTO;
import com.arklimits.shop.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthRestController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest,
        HttpServletRequest request, HttpServletResponse response) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            );
            var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

            // HttpOnly 쿠키에 JWT 저장
            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setMaxAge(60 * 30); // 30분
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            // LocalStorage 에 저장하기 위해, Swagger UI 용도 JWT 반환 
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "로그인 성공");
            successResponse.put("jwt", jwt);
            return ResponseEntity.ok(successResponse);

        } catch (BadCredentialsException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "아이디나 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
