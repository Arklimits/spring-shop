package com.arklimits.shop.filter;

import com.arklimits.shop.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthJwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwtCookie = "";

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                jwtCookie = cookie.getValue();
            }
        }

        // JWT 쿠키가 없을 경우 필터 체인을 그대로 진행
        if (jwtCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authToken = JwtUtil.extractToken(jwtCookie,
                request);

            SecurityContextHolder.getContext().setAuthentication(authToken);

            logger.info("JWT 토큰 검증 완료");
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("JWT 토큰 검증 실패", e);
            filterChain.doFilter(request, response);
            return;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/");
    }

}