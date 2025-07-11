package com.example.post_project_test.service;

import com.example.post_project_test.data.dao.AuthDAO;
import com.example.post_project_test.data.dao.PostDAO;
import com.example.post_project_test.data.dto.PostDTO;
import com.example.post_project_test.data.entity.AuthEntity;
import com.example.post_project_test.data.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDAO postDAO;
    private final AuthDAO authDAO;

    // 글목록 불러오기
    public List<PostDTO> getAllPosts() {
        List<PostEntity> postEntities = postDAO.findAll();
        List<PostDTO> postDTOS = new ArrayList<>();
        for (PostEntity postEntity : postEntities) {
            PostDTO postDTO = PostDTO.builder()
                    .id(postEntity.getId())
                    .title(postEntity.getTitle())
                    .body(postEntity.getBody())
                    .username(postEntity.getUser().getUsername())
                    .build();
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }

    // 글 불러오기
    public PostDTO getPostById(Integer id) {
        PostEntity post = this.postDAO.getPostById(id);
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .username(post.getUser().getUsername())
                .build();
    }

    // 글쓰기
    public PostDTO createPost(PostDTO postDTO) {
        AuthEntity user = this.authDAO.findByUsername(postDTO.getUsername());

        PostEntity postEntity = PostEntity.builder()
                .title(postDTO.getTitle())
                .body(postDTO.getBody())
                .user(user)
                .build();
        PostEntity savedPost = this.postDAO.createPost(postEntity);
        return PostDTO.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .username(savedPost.getUser().getUsername())
                .build();
    }

    // 글수정
    public PostDTO updatePost(PostDTO postDTO) {
        PostEntity postEntity = this.postDAO.getPostById(postDTO.getId());
        if (postEntity == null) {
            throw new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다.");
        }
        // 작성자 확인
        if (!postEntity.getUser().getUsername().equals(postDTO.getUsername())) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setBody(postDTO.getBody());

        PostEntity updatedPost = this.postDAO.updatePost(postEntity);
        return PostDTO.builder()
                .id(updatedPost.getId())
                .title(updatedPost.getTitle())
                .body(updatedPost.getBody())
                .username(updatedPost.getUser().getUsername())
                .build();
    }

    // 글삭제
    public void deletePost(Integer id) {
        this.postDAO.deletePostById(id);
    }

}
