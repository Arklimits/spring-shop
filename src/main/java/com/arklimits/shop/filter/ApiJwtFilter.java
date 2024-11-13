package com.arklimits.shop.filter;

import com.arklimits.shop.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class ApiJwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        // 헤더에 JWT 검사
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            logger.warn("Authorization header not found");
            return;
        }

        // Bearer 문자열 제거
        String token = authorizationHeader.substring(7);

        // 토큰 유효성 검사
        if (token.isBlank()) {
            filterChain.doFilter(request, response);
            logger.warn("Token is blank");
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authToken = JwtUtil.extractToken(token, request);

            SecurityContextHolder.getContext().setAuthentication(authToken);

            logger.info("JWT 토큰 검증 완료");
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("JWT 토큰 검증 실패", e);
            filterChain.doFilter(request, response);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/api/");
    }
}