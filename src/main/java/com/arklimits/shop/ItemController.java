package com.arklimits.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/write")
    String write(Model model) {
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(String title, Integer price) {
        Item item = new Item(title, price);
        itemRepository.save(item);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);

        if (result.isPresent()) {
            model.addAttribute("item", result.get());

            return "detail.html";
        }

        return "redirect:/list";
    }
}
