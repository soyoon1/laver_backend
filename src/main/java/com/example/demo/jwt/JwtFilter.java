package com.example.demo.jwt;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Value("${spring.security.secret}")
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("authorization : " + authorization);

        // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음.
        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
        if(authorization == null || !authorization.startsWith("Bearer ")){
            logger.error("authorization을 잘못 보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분 (Jwt Token) 꺼내기
        String token = authorization.split(" ")[1];
        logger.info("token: " + token);

        // token Expired되었는지 => 다음 필터 진행(인증 X)
        if(JwtUtil.isExpired(token, secretKey)){
            logger.error("Token이 만료 되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // loginId token에서 꺼내기
        String loginId = JwtUtil.getLoginId(token, secretKey);
        logger.info("loginId: " + loginId);

        // 추출한 LoginId로 User 찾아오기
        User loginUser = userService.getLoginUserByLoginId(loginId);

        // 인증된 사용자를 나타내는 토큰 객체를 생성하고, 권한 정보를 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getLoginId(), null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
