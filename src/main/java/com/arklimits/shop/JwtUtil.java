package com.arklimits.shop;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${JWT_SECRET_KEY}")
    private static String secretCode;

    static final SecretKey key =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretCode));

    // JWT 만들어주는 함수
    public static String createToken() {

        String jwt = Jwts.builder()
            // .claim(이름, 값) 으로 JWT에 데이터 추가 가능
            .claim("username", "어쩌구")
            .claim("displayName", "저쩌구")
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 10000)) //유효기간 10초
            .signWith(key)
            .compact();
        return jwt;
    }

    // JWT 까주는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token).getPayload();
        return claims;
    }

}