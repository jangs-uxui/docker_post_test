package com.example.post_project_test.data.dao;

import com.example.post_project_test.data.entity.AuthEntity;
import com.example.post_project_test.data.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthDAO {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원추가
    public AuthEntity addAuth(String username, String password, String fullname) {
        AuthEntity authEntity = AuthEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role("ROLE_USER") // 로직있는경우 Service에서 넘겨받아 수행
                .fullname(fullname)
                .joindate(LocalDate.now())
                .build();
        return authRepository.save(authEntity);
    }

    // 회원정보
    public AuthEntity findByUsername(String username) {
        return authRepository.findById(username).orElse(null);
    }



}
