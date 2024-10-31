package com.arklimits.shop.domain.post.repository;

import com.arklimits.shop.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
