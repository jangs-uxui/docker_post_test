package com.example.post_project_test.configure;

import com.example.post_project_test.component.CustomAccessDeniedHandler;
import com.example.post_project_test.component.CustomAuthEntryPoint;
import com.example.post_project_test.jwt.JwtFilter;
import com.example.post_project_test.jwt.JwtLoginFilter;
import com.example.post_project_test.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration; // AuthenticationManager접근-로그인시 인증수행
    private final CustomAuthEntryPoint customAuthEntryPoint; // 인증실패 예외 (미인증사용자)
    private final CustomAccessDeniedHandler customAccessDeniedHandler; // 로그인했지만 권한X
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager(); // AuthenticationManager를 빈으로 등록해 인증에 사용
    }


    // 메인 인증로직 (JWT)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                // 요청별 권한
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/","/api/login","/api/user","/api/reissue","/api/posts","/api/post").permitAll();
                    requests.requestMatchers("/api/admin/**").hasRole("ADMIN");
                    requests.requestMatchers("/api/post/**").hasAnyRole("USER", "ADMIN");
                    requests.anyRequest().authenticated();
                })
                // CORS설정
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("http://localhost");
                    corsConfiguration.addAllowedOrigin("http://15.164.140.157");
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.setExposedHeaders(List.of("Authorization")); // 브라우저가 응답헤더 중 Authorization 헤더 접근 허용
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowCredentials(true); // 쿠키허용
                    return corsConfiguration;
                }))
                // 세션사용X (JWT인증 사용)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 필터
                .addFilterBefore(new JwtFilter(this.jwtUtil), JwtLoginFilter.class) // 로그인필터 앞에 Jwt필터 위치
                .addFilterAt(new JwtLoginFilter(authenticationManager(this.authenticationConfiguration), this.jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // 예외처리 (인증정보 없을 때)
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(this.customAuthEntryPoint);
                    exception.accessDeniedHandler(this.customAccessDeniedHandler);
                });

        return http.build();
    }


}
