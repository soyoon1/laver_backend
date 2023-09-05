package com.example.demo.jwt;

import com.example.demo.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


@Slf4j
@Component
public class JwtUtil {

    // 시크릿 키를 담는 변수
    private static SecretKey cachedSecretKey;

    // plain 시크릿 키를 담는 변수
//    @Value("${spring.security.secret}")
    public static String secretKeyPlain;

    @Value("${spring.security.secret}")
    public void setSecretKeyPlain(String temp){
        secretKeyPlain = temp;
    }

    // plain -> 시크릿 키 변환 method
    private static SecretKey _getSecretKey(){
        log.info(secretKeyPlain);
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    // 시크릿 키를 반환하는 method
    public static SecretKey getSecretKey(){
        if(cachedSecretKey == null) cachedSecretKey = _getSecretKey();
        return cachedSecretKey;
    }

    // JWT Token 발급
    public static String createToken(int id, String loginId, Role role, long expireTimeMs){
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("userId", id);
        claims.put("loginId", loginId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Claims에서 loginId 꺼내기
    public static String getLoginId(String token){
        return extractClaims(token).get("loginId").toString();
    }

    public static String getUserId(String token){
        return extractClaims(token).get("userId").toString();
    }

    // 발급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token){  //, String secretKey
        Date expriedDate = extractClaims(token).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expriedDate.before(new Date());
    }

    // SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token){
//        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
    }

//    public static int getCurrentMemberId(){
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if(authentication == null || authentication.getName() == null){
//            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
//        }
//
//        return Integer.parseInt(authentication.getName());
//    }


    public static int getCurrentMemberId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }

        String username = authentication.getName();

        if (!username.matches("\\d+")) {
            throw new RuntimeException("올바르지 않은 사용자 ID 형식입니다.");
        }

        return Integer.parseInt(username);
    }

}
