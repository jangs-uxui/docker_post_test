package com.example.post_project_test.data.dto;

import com.example.post_project_test.data.entity.AuthEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Integer id;
    private String title;
    private String body;
    private String username;
}
