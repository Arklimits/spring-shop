package com.arklimits.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);

        return "list";
    }

    @GetMapping("/write")
    String write(Model model) {
        return "write";
    }

    @PostMapping("/add")
    String addPost(String title, Integer price) {
        itemService.saveItem(title, price);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return itemService.findItemById(id).map(item -> {
            model.addAttribute("item", item);
            return "detail";
        }).orElse("redirect:/list");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        return itemService.findItemById(id).map(item -> {
            model.addAttribute("item", item);
            return "edit";
        }).orElse("redirect:/list");
    }

    @PostMapping("/edit")
    public String editItem(Long id, String title, Integer price) {
        itemService.editItem(id, title, price);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }
}
