package com.example.post_project_test.controller;

import com.example.post_project_test.data.dto.AuthDTO;
import com.example.post_project_test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AuthController {
    private final AuthService authService;

    // 회원가입
    @PostMapping("/user")
    public ResponseEntity<String> addUser(@Valid @RequestBody AuthDTO authDTO, BindingResult result) {
        if (result.hasErrors()) { // 유효성 검사 실패시
            String message = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        }
        authService.addAuth(authDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("가입성공");
    }



}
