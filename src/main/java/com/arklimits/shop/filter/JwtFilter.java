package com.arklimits.shop.filter;

import com.arklimits.shop.domain.member.security.CustomUser;
import com.arklimits.shop.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

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
            logger.info("JWT 쿠키가 존재하지 않음.");
            filterChain.doFilter(request, response);
            return;
        }

        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);
        } catch (Exception e) {
            logger.error("JWT 토큰 검증 실패", e);
            filterChain.doFilter(request, response);
            return;
        }

        var arr = claim.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr).map(SimpleGrantedAuthority::new).toList();

        CustomUser customUser = new CustomUser(
            claim.get("username").toString(),
            "none",
            authorities);

        customUser.setDisplayName(claim.get("displayName", String.class));

        var authToken = new UsernamePasswordAuthenticationToken(customUser, "", authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info("인증 성공: " + auth.getName());
        } else {
            logger.error("인증 정보가 설정되지 않음");
        }

        logger.info("인증 정보 확인 완료.");

        filterChain.doFilter(request, response);
    }

}