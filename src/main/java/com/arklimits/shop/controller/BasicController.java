package com.arklimits.shop.controller;

import com.arklimits.shop.domain.post.entity.Post;
import com.arklimits.shop.domain.post.service.PostService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final PostService postService;

    @GetMapping("/")
    String home(Model model) {

        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "main/index";
    }

    @GetMapping("/about")
    @ResponseBody
    String about() {
        return "Site for practice Spring Boot";
    }

    @GetMapping("/date")
    @ResponseBody
    LocalDateTime date() {
        return LocalDateTime.now();
    }
}
