package com.example.post_project_test.exception;

// 사용자 정의 예외 클래스
public class DuplicateIdException extends RuntimeException {
    public DuplicateIdException(String message) {
        super(message);
    }
}
