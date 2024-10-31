package com.arklimits.shop.domain.item.controller;

import com.arklimits.shop.domain.comment.entity.Comment;
import com.arklimits.shop.domain.comment.service.CommentService;
import com.arklimits.shop.domain.item.entity.Item;
import com.arklimits.shop.domain.item.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    String getListPage(@RequestParam(required = false) String keywords,
        @RequestParam(defaultValue = "1") Integer page,
        Model model) {

        Page<Item> result = (keywords != null && !keywords.isBlank())
            ? itemService.searchItems(keywords, page)
            : itemService.listAllItems(page);

        model.addAttribute("items", result);
        model.addAttribute("page", page);
        model.addAttribute("pages", result.getTotalPages());

        if (keywords != null && !keywords.isBlank()) {
            model.addAttribute("keywords", keywords);
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
        return "redirect:/item/list";
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
        }).orElse("redirect:/item/list");
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        return itemService.findItemById(id).map(item -> {
            model.addAttribute("item", item);
            return "edit";
        }).orElse("redirect:/item/list");
    }

    @PostMapping("/edit")
    public String editItem(@RequestParam Long id, @RequestParam String title,
        @RequestParam Integer price, @RequestParam String imageUrl) {
        itemService.editItem(id, title, price, imageUrl);
        return "redirect:/item/list";
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }
}
