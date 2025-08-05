// JwtAuthenticationFilter.java
package com.finday.security;

import com.finday.backend.user.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("🔥🔥🔥 JwtAuthenticationFilter 진입: " + request.getRequestURI());
            filterChain.doFilter(request, response); // ✅ 필터 체인 계속 태움
            return;
        }

        String path = request.getRequestURI();
        System.out.println(path);
        if (path.startsWith("/api/user/auth")) {
            System.out.println("jwt 검증이 필요하지 않은 로직입니다");
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("jwt 검증이 필요한 로직입니다");

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        // 토큰이 없거나 잘못된 형식일 경우 필터 통과 (로그인/회원가입 등)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("Extracted Token: " + token);

        try {
            Long userNo = jwtUtil.validateAndGetUserNo(token);
            request.setAttribute("userNo", userNo);
            System.out.println("userNo : " + userNo);

            // ✅ 인증 객체 생성 및 SecurityContext에 설정
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userNo, null, Collections.emptyList()  // 권한이 없으면 빈 리스트
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            e.printStackTrace();
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
