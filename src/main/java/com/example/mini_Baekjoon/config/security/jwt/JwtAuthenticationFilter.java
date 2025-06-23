package com.example.mini_Baekjoon.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("=== 요청 시작 ===");
        System.out.println("요청 URI: " + request.getRequestURI());

        String token = jwtTokenProvider.resolveToken(request);
        System.out.println("추출된 토큰: " + token);

        if (token != null) {
            System.out.println("토큰 검증 시도...");
            if (jwtTokenProvider.validateToken(token)) {
                System.out.println("토큰 유효. 인증 정보 설정");
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                System.out.println("토큰 검증 실패");
            }
        } else {
            System.out.println("토큰 없음");
        }

        chain.doFilter(request, response);
        System.out.println("=== 요청 처리 완료 ===");
    }

}
