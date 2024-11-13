package com.arklimits.shop.domain.item.controller;

import com.arklimits.shop.domain.comment.entity.Comment;
import com.arklimits.shop.domain.comment.service.CommentService;
import com.arklimits.shop.domain.item.entity.Item;
import com.arklimits.shop.domain.item.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CommentService commentService;

    @GetMapping("/list")
    String getListPage(@RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "1") Integer page,
        Model model) {

        Page<Item> result = (keyword != null && !keyword.isBlank())
            ? itemService.searchItems(keyword, page)
            : itemService.listAllItems(page);

        model.addAttribute("items", result.getContent());
        model.addAttribute("page", page);
        model.addAttribute("pages", result.getTotalPages());

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("keyword", keyword);
        }

        return "item/list";
    }

    @GetMapping("/upload")
    String write() {
        return "item/upload";
    }

    @PostMapping("/add")
    String addPost(@RequestParam String title, @RequestParam Integer price, String imageUrl) {
        itemService.saveItem(title, price, imageUrl);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return itemService.findItemById(id).map(item -> {
            // 아이템 정보 추가
            model.addAttribute("item", item);

            // 댓글 리스트 가져오기
            List<Comment> comments = commentService.findCommentsOfPage(id);
            model.addAttribute("comments", comments);

            return "item/detail";
        }).orElse("redirect:/list");
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        return itemService.findItemById(id).map(item -> {
            model.addAttribute("item", item);
            return "item/edit";
        }).orElse("redirect:/list");
    }
}
