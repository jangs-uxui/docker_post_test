package com.example.post_project_test.service;

import com.example.post_project_test.data.dao.AuthDAO;
import com.example.post_project_test.data.dto.AuthDTO;
import com.example.post_project_test.data.entity.AuthEntity;
import com.example.post_project_test.exception.DuplicateIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthDAO authDAO;

    // 인증처리 - 사용자정보 DB에서 로딩
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthEntity authEntity = this.authDAO.findByUsername(username); // user정보
        if (authEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        // role 권한 세팅
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(authEntity.getRole())); // role을 변환 (GrantedAuthority)
        return new User(authEntity.getUsername(), authEntity.getPassword(), grantedAuthorities);
    }

    // 회원추가
    public AuthDTO addAuth(AuthDTO authDTO) {
        if (authDTO.getUsername() == null || authDTO.getPassword() == null || authDTO.getFullname() == null) {
            throw new IllegalArgumentException("username과 password, 이름은 필수입니다.");
        }
        if (authDAO.findByUsername(authDTO.getUsername()) != null) {
            throw new DuplicateIdException("동일ID가 존재합니다.");
        }
        authDAO.addAuth(authDTO.getUsername(), authDTO.getPassword(), authDTO.getFullname()); // DAO넘겨서 저장
        AuthDTO saveAuthDTO = AuthDTO.builder() // return용
                .username(authDTO.getUsername())
                .fullname(authDTO.getFullname())
                .build();
        return saveAuthDTO;
    }



}
