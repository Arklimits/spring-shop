package com.arklimits.shop.item.controller;

import com.arklimits.shop.comment.entity.Comment;
import com.arklimits.shop.comment.service.CommentService;
import com.arklimits.shop.item.S3Service;
import com.arklimits.shop.item.entity.Item;
import com.arklimits.shop.item.repository.ItemRepository;
import com.arklimits.shop.item.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final CommentService commentService;
    private final S3Service s3Service;

    @GetMapping("/list")
    String getListPage(@RequestParam(defaultValue = "1") Integer page, Model model) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(page - 1, 5));
        model.addAttribute("items", result);
        model.addAttribute("page", page);
        model.addAttribute("pages", result.getTotalPages());
        return "list";
    }

    @GetMapping("/search")
    String searchItem(@RequestParam String keywords, @RequestParam(defaultValue = "1") Integer page,
        Model model) {
        Page<Item> result = itemRepository.findPageByTitleContains(keywords,
            PageRequest.of(page - 1, 5));
        model.addAttribute("items", result);
        model.addAttribute("pages", result.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);

        return "search";
    }

    @GetMapping("/write")
    String write(Model model) {
        return "write";
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

            return "detail";
        }).orElse("redirect:/list");
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        return itemService.findItemById(id).map(item -> {
            model.addAttribute("item", item);
            return "edit";
        }).orElse("redirect:/list");
    }

    @PostMapping("/edit")
    public String editItem(@RequestParam Long id, @RequestParam String title,
        @RequestParam Integer price, @RequestParam String imageUrl) {
        itemService.editItem(id, title, price, imageUrl);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename) {
        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }
}
