package com.arklimits.shop.domain.comment.controller;

import com.arklimits.shop.domain.comment.service.CommentService;
import com.arklimits.shop.domain.member.security.CustomUser;
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
    String postComment(Integer rating, String content, Long itemId, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        String username = user.getUsername();
        String displayName = user.getDisplayName();

        commentService.saveComment(username, displayName, rating, content, itemId);

        return "redirect:/detail/" + itemId;
    }

}
