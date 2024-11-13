package com.arklimits.shop.util;

import com.arklimits.shop.domain.member.security.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static SecretKey key;

    @Value("${jwt.secret}")
    private void setSecretCode(String secretCode) {
        JwtUtil.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretCode));
    }

    // JWT 만들어주는 함수
    public static String createToken(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        String authorities = auth.getAuthorities().stream().map(
                GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        return Jwts.builder()
            // .claim(이름, 값) 으로 JWT에 데이터 추가 가능
            .claim("id", user.getId())
            .claim("username", user.getUsername())
            .claim("displayName", user.getDisplayName())
            .claim("authorities", authorities)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) //유효기간 30분
            .signWith(key)
            .compact();
    }

    // JWT 까주는 함수
    public static UsernamePasswordAuthenticationToken extractToken(String token,
        HttpServletRequest request) {
        Claims claims = Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token).getPayload();

        var arr = claims.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr).map(SimpleGrantedAuthority::new).toList();

        CustomUser customUser = new CustomUser(
            claims.get("username").toString(),
            "none",
            authorities);

        customUser.setId(claims.get("id", Double.class).longValue());
        customUser.setDisplayName(claims.get("displayName", String.class));

        var authToken = new UsernamePasswordAuthenticationToken(customUser, "", authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authToken;
    }

}