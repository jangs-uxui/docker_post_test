package com.example.post_project_test.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// JWT 로그인 처리***
//@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter { // 필터 덮어쓰기
    private final AuthenticationManager authenticationManager; // 인증처리 인터페이스  (성공시 Authentication객체반환 / 실패시 예외발생)
    private final JwtUtil jwtUtil;

    // 생성자
    public JwtLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/api/login"); // 로그인경로 커스텀
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request); // 프론트에서 받은정보 추출
        String password = obtainPassword(request);

        // 인증용 토큰 생성
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, null);
        return authenticationManager.authenticate(authRequest); // 인증수행**
        // 인증성공 successfulAuthentication() / 인증실패 unsuccessfulAuthentication()
    }

    // 인증성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authResult.getPrincipal(); // 인증된 사용자정보 추출
        String username = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities(); // 유저권한 목록 추출
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority(); // 권한명 문자열로

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("username", username);
        responseData.put("role", role);
        responseData.put("result", "로그인 성공");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(responseData);

        // 토큰 생성**
        String accessToken = this.jwtUtil.generateToken("access", username, role, 10*1000L); // access토큰 생성
        String refreshToken = this.jwtUtil.generateToken("refresh", username, role, 60*60*24*1000L); // refresh토큰 생성

        response.addHeader("Authorization", "Bearer " + accessToken); // 헤더삽입
        response.addCookie(this.createCookie("refresh", refreshToken)); // 쿠키삽입

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK); // 200
        response.getWriter().write(jsonMessage);
    }

    // 인증실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("result", "로그인 실패");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(responseData); // json문자열 변환

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.getWriter().write(jsonMessage);
    }

    // 쿠키생성 (refreshToken)
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24);
        return cookie;
    }










}
