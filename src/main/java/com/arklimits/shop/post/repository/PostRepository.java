package com.arklimits.shop.post.repository;

import com.arklimits.shop.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
