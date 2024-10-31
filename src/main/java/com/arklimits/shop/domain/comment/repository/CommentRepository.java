package com.arklimits.shop.domain.comment.repository;

import com.arklimits.shop.domain.comment.entity.Comment;
import com.arklimits.shop.domain.item.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByItem(Item item);
}
