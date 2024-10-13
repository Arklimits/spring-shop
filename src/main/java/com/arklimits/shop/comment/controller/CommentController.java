package com.arklimits.shop.comment.controller;

import com.arklimits.shop.comment.service.CommentService;
import com.arklimits.shop.member.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment")
    String postComment(Integer rating, String content, Long parentId, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        String username = user.getUsername();

        commentService.saveComment(username, rating, content, parentId);

        return "redirect:/detail/" + parentId;
    }

}
