package com.arklimits.shop.domain.post.service;

import com.arklimits.shop.domain.post.entity.Post;
import com.arklimits.shop.domain.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
