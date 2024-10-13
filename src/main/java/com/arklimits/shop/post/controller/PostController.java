package com.arklimits.shop.post.controller;

import com.arklimits.shop.post.entity.Post;
import com.arklimits.shop.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @GetMapping("/notice")
    String notice(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "notice.html";
    }
}
