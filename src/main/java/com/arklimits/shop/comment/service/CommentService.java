package com.arklimits.shop.comment.service;

import com.arklimits.shop.comment.entity.Comment;
import com.arklimits.shop.comment.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void saveComment(String username, String displayName, Integer rating, String content,
        Long parentId) {
        Comment comment = new Comment(username, displayName, rating, content, parentId);

        commentRepository.save(comment);
    }

    public List<Comment> findCommentsOfPage(Long parentId) {
        return commentRepository.findAllByParentId(parentId);
    }
}