package com.example.post_project_test.data.dao;

import com.example.post_project_test.data.entity.PostEntity;
import com.example.post_project_test.data.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostDAO {
    private final PostRepository postRepository;

    // 글목록 불러오기
    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    // 글 불러오기
    public PostEntity getPostById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    // 글쓰기
    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    // 글수정
    public PostEntity updatePost(PostEntity postEntity) {
        return postRepository.findById(postEntity.getId())
                .map(post -> { // 게시글 있을때만 작동
                    post.setTitle(postEntity.getTitle());
                    post.setBody(postEntity.getBody());
                    // post.setUser(postEntity.getUser()); // 작성자는 바뀌지 않음
                    return postRepository.save(post);
                })
                .orElse(null);
    }

    // 글 삭제
    public void deletePostById(Integer id) {
        postRepository.deleteById(id);
    }


}
