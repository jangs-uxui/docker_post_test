package com.example.post_project_test.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// JWT 인증처리
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // 요청당 1번만 실행 필터
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization"); // 요청헤더에서 Authorization추출
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.split(" ")[1]; // 토큰값만 분리

        // 유효기간 확인
        try {
            this.jwtUtil.isTokenExpired(token); // 유효기간지나면 예외발생
        } catch (ExpiredJwtException e) {
            response.getWriter().write("AccessToken is expired");
            response.setStatus(456); // access토큰만료
            return;
        }

        // 토큰정보 추출
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role)); // 문자열role을 GrantedAuthority객체로 포장

        // 인증
        User user = new User(username, "", authorities); // User에 포장
        Authentication auth = new UsernamePasswordAuthenticationToken(user, "", authorities); // 다시 포장
        SecurityContextHolder.getContext().setAuthentication(auth); // 인증정보 임시저장
        filterChain.doFilter(request, response); // 다음필터로 넘김
    }


}
