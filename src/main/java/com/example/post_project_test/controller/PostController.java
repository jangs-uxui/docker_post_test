package com.example.post_project_test.controller;

import com.example.post_project_test.data.dto.PostDTO;
import com.example.post_project_test.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class PostController {
    private final PostService postService;

    // 글목록 불러오기
    @GetMapping(value = "/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPosts());
    }

    // 글 불러오기
    @GetMapping(value = "/post/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getPostById(id));
    }

    // 글쓰기
    @PostMapping(value = "/post")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.createPost(postDTO));
    }

    // 수정하기
    @PutMapping(value = "/post")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.updatePost(postDTO));
    }

    // 글삭제
    @DeleteMapping(value = "/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        try {
            this.postService.deletePost(id);
            return ResponseEntity.status(HttpStatus.OK).body("삭제성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }




}
