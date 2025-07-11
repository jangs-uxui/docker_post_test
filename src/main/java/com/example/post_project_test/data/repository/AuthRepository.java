package com.example.post_project_test.data.repository;

import com.example.post_project_test.data.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthEntity, String> {
}
