package com.arklimits.shop.domain.comment.service;

import com.arklimits.shop.domain.comment.entity.Comment;
import com.arklimits.shop.domain.comment.repository.CommentRepository;
import com.arklimits.shop.domain.item.entity.Item;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void saveComment(String username, String displayName, Integer rating, String content,
        Long itemId) {
        Item item = new Item();
        item.setId(itemId);

        Comment comment = new Comment(username, displayName, rating, content, item);

        commentRepository.save(comment);
    }

    public List<Comment> findCommentsOfPage(Long itemId) {
        Item item = new Item();
        item.setId(itemId);

        return commentRepository.findAllByItem(item);
    }
}
