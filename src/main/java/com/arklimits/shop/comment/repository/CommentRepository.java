package com.arklimits.shop.comment.repository;

import com.arklimits.shop.comment.entity.Comment;
import com.arklimits.shop.item.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByItem(Item item);
}
